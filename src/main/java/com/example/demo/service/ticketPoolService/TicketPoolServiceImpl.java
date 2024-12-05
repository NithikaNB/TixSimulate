package com.example.demo.service.ticketPoolService;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.Task.CustomerTask;
import com.example.demo.service.Task.TaskFactory;
import com.example.demo.service.Task.VendorTask;
import com.example.demo.service.customerService.CustomerService;
import com.example.demo.service.vendorService.VendorService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Service
public class TicketPoolServiceImpl implements TicketPoolService{

    // ATTRIBUTES //
//    private final TicketPoolRepository ticketPoolRepository;
    private final TaskFactory taskFactory;
    private final Map<Long, TicketPool> ticketPoolMap = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(TicketPoolServiceImpl.class.getName());
    private final ExecutorService executorService = Executors.newCachedThreadPool();


    // CONSTRUCTOR //

    public TicketPoolServiceImpl(@Lazy TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }


    // METHODS //

    // Method to create a ticket pool
    @Override
    public void createTicketPool(String ticketPoolName, int ticketCount) {
        if (ticketPoolMap.containsValue(ticketPoolName)){
            throw new IllegalArgumentException("Ticket pool with the name: " + ticketPoolName + " already exists!");
        }

        // Creation of a new ticketPool object
        TicketPool ticketPool = new TicketPool(ticketPoolName, ticketCount);
        long ticketPoolId = ticketPool.getTicketPoolId();

        // Storing the created object in a HashMap
        ticketPoolMap.put(ticketPoolId, ticketPool);

        // Logging
        System.out.println("Ticket pool '" + ticketPoolName + "' (ID: " + ticketPoolId + ") has been successfully created.");
        logger.info("Ticket pool '" + ticketPoolName + "' (ID: " + ticketPoolId + ") has been successfully created.");
    }


    // Method to add ticket (Thread safe)
    @Override
    public synchronized void addTicket(Long ticketPoolId, int ticketCount) {

        // Check whether the entered ticket count is greater than zero
        if(ticketCount <= 0){
            throw new IllegalArgumentException("Ticket count must be greater than zero.");
        }

        // Retrieve ticket pool from the hash map
        TicketPool ticketPool = ticketPoolMap.get(ticketPoolId);

        // Check whether there exist a ticket pool with provided ID
        if(ticketPool == null){
            throw new IllegalArgumentException("Ticket pool not found for ID: "+ ticketPoolId);
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
        if (ticketPool == null){
            throw new IllegalArgumentException("Ticket pool not found for ID: " + ticketPoolId);
        }

        // Check whether needed ticket count doesn't exceed the available ticket count
        if (ticketCount > ticketPool.getAvailableTickets()){
            throw new IllegalArgumentException("Not enough tickets available to remove.");
        }

        // Remove tickets
        ticketPool.removeTickets(ticketCount);

        // Logging
//        logger.info(ticketCount + " tickets removed from ticket pool: " + ticketPool.getTicketPoolName());
//        System.out.println(ticketCount + " tickets removed from ticket pool: " + ticketPool.getTicketPoolName());


    }

    // Method to get the ticket pool object from the hash map by using ID
    @Override
    public TicketPool getTicketPoolById(Long ticketPoolId) {
        // Retrieve ticket pool from the hash map
        TicketPool ticketPool = ticketPoolMap.get(ticketPoolId);

        // Check whether the object exists
        if (ticketPool == null){
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
        for (TicketPool ticketPoolObj : ticketPoolMap.values()){
            if(ticketPoolObj.getTicketPoolName().equals(ticketPoolName)){
                ticketPool = ticketPoolObj;
                break;
            }
        }

        long ticketPoolId = ticketPool.getTicketPoolId();
        // Check whether the object exists
        if (ticketPool == null){
            throw new IllegalArgumentException("Ticket pool not found for ID: " + ticketPoolId);
        }

        // Return the ticketPool object if exists
        return ticketPool;
    }

    @Override
    public int getAvailableTickets(String ticketPoolName) {
        TicketPool ticketPool = getTicketPoolByName(ticketPoolName);
        return ticketPool.getAvailableTickets();
    }

    @Override
    public void sampleTask() {
        //Create a Sample TicketPool object
        // 1. Create and retrieve TicketPool
        createTicketPool("movie", 100);
        TicketPool ticketPool = getTicketPoolByName("movie");

        // 2. Create Customers and Vendors using their respective services
        Customer customer1 = new Customer("Alice", 15, 5, 0, true);  // Example customer
        Customer customer2 = new Customer("Bob", 10, 10, 0,true);    // Example customer
        Vendor vendor1 = new Vendor("Vendor A", 1, 10, true);  // Example vendor
        Vendor vendor2 = new Vendor("Vendor B", 1, 5, true);  // Example vendor

        CustomerTask customerTask1 = taskFactory.createCustomerTask(customer1, ticketPool);
        CustomerTask customerTask2 = taskFactory.createCustomerTask(customer2, ticketPool);
        VendorTask vendorTask1 = taskFactory.createVendorTask(vendor1, ticketPool);
        VendorTask vendorTask2 = taskFactory.createVendorTask(vendor2, ticketPool);

        executorService.submit(customerTask1);
        executorService.submit(customerTask2);
        executorService.submit(vendorTask1);
        executorService.submit(vendorTask2);
        // 6. Log task start
        logger.info("All Customer and Vendor tasks have been started.");
    }

}
