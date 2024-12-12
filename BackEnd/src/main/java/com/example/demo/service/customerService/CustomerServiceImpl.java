package com.example.demo.service.customerService;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.service.task.CustomerTask;
import com.example.demo.service.task.TaskFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CustomerServiceImpl implements CustomerService{

    // ATTRIBUTES //
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final Map<Long, Customer> customerMap = new ConcurrentHashMap<>();
    private static long idCounter = 0;

    // Creates a fixed thread pool with 10 threads to handle concurrent tasks efficiently.
    // The ExecutorService manages the lifecycle of the threads and executes tasks asynchronously.
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final TaskFactory taskFactory;


    // CONSTRUCTOR //
    public CustomerServiceImpl(TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    // METHODS //

    // Method to create customers
    @Override
    public void createCustomer(Customer customer) {
        // Validate that retrievalInterval is greater than zero
        if (customer.getRetrievalInterval() <= 0) {
            throw new IllegalArgumentException("Retrieval interval must be greater than zero.");
        }

        // Validate that ticketsRequested is greater than zero
        if (customer.getTicketsRequested() <= 0) {
            throw new IllegalArgumentException("Tickets requested must be greater than zero.");
        }

        // Validate that ticketsPurchased is not negative
        if (customer.getTicketsPurchased() < 0) {
            throw new IllegalArgumentException("Tickets purchased cannot be negative.");
        }

        // Assign a unique customer ID
        long customerId = idCounter;
        ++idCounter;
        customer.setCustomerId(customerId);
        // Save the customer in a data structure (e.g., hashmap or database)
        customerMap.put(customerId, customer);

        logger.info("Customer created: " + customer.getCustomerName() + " with ID: " + customer.getCustomerId());

    }

    // Method to find customer objects saved in the hashmap using names
    @Override
    public Customer findCustomerByName(String customerName) {
        // Check whether the customerName is empty
        if (customerName == null || customerName.trim().isEmpty()) {
            logger.error("Invalid customer name provided for search.");
            throw new IllegalArgumentException("Customer name must not be null or empty.");
        }

        // Iterating each customer object in the hashmap to find a match
        for (Customer customer : customerMap.values()) {
            if (customer.getCustomerName().equalsIgnoreCase(customerName)) {
                logger.info("Customer found: " + customerName);
                return customer;
            }
        }

        // When fails to find a match
        logger.error("Customer not found with name: " + customerName);
        throw new IllegalArgumentException("Customer not found with name: " + customerName);
    }


    // Method to find customer objects saved in the hashmap using customerId
    @Override
    public Customer findCustomerById(Long customerId) {
        // Check whether the customerId is empty
        if (customerId == null) {
            logger.error("Invalid customer ID provided for search.");
            throw new IllegalArgumentException("Customer ID must not be null.");
        }

        Customer customer = customerMap.get(customerId);
        if (customer != null) {
            logger.info("Customer found with ID: " + customerId);
            return customer;
        }

        // When fails to find a match
        logger.error("Customer not found with ID: " + customerId);
        throw new IllegalArgumentException("Customer not found with ID: " + customerId);
    }
}