package com.example.demo.service.ticketPoolService;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.customerService.CustomerServiceImpl;
import com.example.demo.service.task.CustomerTask;
import com.example.demo.service.task.TaskFactory;
import com.example.demo.service.task.VendorTask;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TicketPoolServiceImpl implements TicketPoolService {

    // ATTRIBUTES //
    private final TaskFactory taskFactory;
    private final Map<Long, TicketPool> ticketPoolMap = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(TicketPoolServiceImpl.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private static long idCounter = 0;


    // CONSTRUCTOR //
    public TicketPoolServiceImpl(@Lazy TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }


    // METHODS //

    // Method to create a ticket pool
    @Override
    public void createTicketPool(String ticketPoolName, int ticketCount, int maxTicketCapacity) {
        if (ticketPoolMap.containsValue(ticketPoolName)) {
            throw new IllegalArgumentException("Ticket pool with the name: " + ticketPoolName + " already exists!");
        }


        // Creation of a new ticketPool object
        TicketPool ticketPool = new TicketPool(ticketPoolName, ticketCount, maxTicketCapacity);

        // get the configurationId
        long ticketPoolId = idCounter;
        ++idCounter;
        ticketPool.setTicketPoolId(ticketPoolId);

        // Storing the created object in a HashMap
        ticketPoolMap.put(ticketPoolId, ticketPool);

        // Logging
        logger.info("Ticket pool '" + ticketPoolName + "' (ID: " + ticketPoolId + ") has been successfully created.");
    }


    // Method to add ticket (Thread safe)
    @Override
    public synchronized void addTicket(Long ticketPoolId, int ticketCount) {

        // Check whether the entered ticket count is greater than zero
        if (ticketCount <= 0) {
            throw new IllegalArgumentException("Ticket count must be greater than zero.");
        }

        // Retrieve ticket pool from the hash map
        TicketPool ticketPool = ticketPoolMap.get(ticketPoolId);


        // Check whether there exist a ticket pool with provided ID
        if (ticketPool == null) {
            throw new IllegalArgumentException("Ticket pool not found for ID: " + ticketPoolId);
        }

        // Adding tickets to the ticket
        ticketPool.addTickets(ticketCount);

        // Logging
//        logger.info(ticketCount + " tickets added to the ticket pool " + ticketPool.getTicketPoolName());

    }

    // Method to remove tickets (Thread safe)
    @Override
    public synchronized void removeTicket(Long ticketPoolId, int ticketCount) {
        // Validate ticketCount
        if (ticketCount <= 0) {
            throw new IllegalArgumentException("Ticket count must be greater than zero.");
        }

        // Retrieve ticket pool from the hash map
        TicketPool ticketPool = ticketPoolMap.get(ticketPoolId);

        // Check whether the object exists
        if (ticketPool == null) {
            throw new IllegalArgumentException("Ticket pool not found for ID: " + ticketPoolId);
        }

        // Check whether needed ticket count doesn't exceed the available ticket count
        if (ticketCount > ticketPool.getAvailableTickets()) {
            throw new IllegalArgumentException("Not enough tickets available to remove.");
        }

        // Remove tickets
        ticketPool.removeTickets(ticketCount);


    }

    // Method to get the ticket pool object from the hash map by using ID
    @Override
    public TicketPool getTicketPoolById(Long ticketPoolId) {
        // Retrieve ticket pool from the hash map
        TicketPool ticketPool = ticketPoolMap.get(ticketPoolId);

        // Check whether the object exists
        if (ticketPool == null) {
            throw new IllegalArgumentException("Ticket pool not found for ID: " + ticketPoolId);
        }

        // Return the ticketPool object if exists
        return ticketPool;
    }

    // Method to get the ticket pool object from the hash map by using Name
    @Override
    public TicketPool getTicketPoolByName(String ticketPoolName) {
        // Creates an empty ticketPool object
        TicketPool ticketPool = null;

        // Iterating each ticket pool value of the hashmap and retrieve a value that has the name of the required ticket pool
        for (TicketPool ticketPoolObj : ticketPoolMap.values()) {
            if (ticketPoolObj.getTicketPoolName().equals(ticketPoolName)) {
                ticketPool = ticketPoolObj;
                break;
            }
        }

        // Check whether the object exists
        try {
            long ticketPoolId = ticketPool.getTicketPoolId();
        } catch (NullPointerException e) {
            logger.error("Ticket pool not found for Name: " + ticketPoolName);
        }


        // Return the ticketPool object if exists
        return ticketPool;
    }

    // Method to get all available tickets
    @Override
    public synchronized int getAvailableTickets(String ticketPoolName) {
        TicketPool ticketPool = getTicketPoolByName(ticketPoolName);
        if (ticketPool == null) {

            createTicketPool(ticketPoolName, 0, 100); // Default values
            ticketPool = getTicketPoolByName(ticketPoolName);
        }
        return ticketPool.getAvailableTickets();
    }


    // This method will create a ticket pool before the threads begin
    @PostConstruct
    public void initializeDefaultTicketPool() {
        if (getTicketPoolByName("movie") == null) {
            createTicketPool("movie", 0, 100); // Adjust default values
            logger.info("Default ticket pool 'movie' created.");
        }

    }

}
