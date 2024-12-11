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
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final Map<Long, Customer> customerMap = new ConcurrentHashMap<>();

    // Creates a fixed thread pool with 10 threads to handle concurrent tasks efficiently.
    // The ExecutorService manages the lifecycle of the threads and executes tasks asynchronously.
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final TaskFactory taskFactory;

    public CustomerServiceImpl(TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    @Override
    public void createCustomer(Customer customer){
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

        // Assign a unique customer ID (if using an auto-generated ID, ensure it's set)
        long customerId = customer.getCustomerId();
        // Save the customer in a data structure (e.g., hashmap or database)
        customerMap.put(customerId, customer);

        logger.info("Customer created: " + customer.getCustomerName() + " with ID: " + customer.getCustomerId());

    }

//    @Override
//    public void startCustomerTask(Long customerId) {
//        try {
//            // Create a Customer object
//            Customer customer1 = new Customer("Customer1", 10, 5, 0, true);
//            logger.info("Customer object created: " + customer1.getCustomerName());
//
//            // Create a Ticket Pool using the Service Class
//            String ticketPoolName = "Movie";  // Name of the ticket pool
//            int ticketCount = 100;           // Total tickets available
//            ticketPoolService.createTicketPool(ticketPoolName, ticketCount);
//            logger.info("Ticket pool created: " + ticketPoolName + " with " + ticketCount + " tickets");
//
//            // Retrieve the Ticket Pool object
//            TicketPool ticketPool = ticketPoolService.getTicketPoolByName(ticketPoolName);
//
//            // Create a CustomerTask and submit it for execution
//            CustomerTask customerTask = new CustomerTask(customer1, ticketPool, ticketPoolService);
//            logger.info("CustomerTask object created for execution");
//            executorService.submit(customerTask);
//            logger.info("Task submitted for execution...");
//        } catch (Exception e) {
//            // Handle and log any exceptions
//            String message = "Error occurred: " + e.getMessage();
//            System.out.println(message);
//            logger.error(message);
//        }
//    }


    @Override
    public void startCustomerTask(Long customerId, TicketPool ticketPool) {
        // Get the customer object stored inside the hashmap
        Customer customer = customerMap.get(customerId);

        // Check whether there exists a customer object according to the provided customerId
        if (customer == null){
            throw new IllegalArgumentException("Customer not found for ID: " + customerId);
        }

        // Pass customer, ticketPool, ticketPoolService objects into CustomerTask and create a customerTask object
        CustomerTask customerTask = taskFactory.createCustomerTask(customer, ticketPool);
        executorService.submit(customerTask);
        logger.info("Customer task started for customer ID: " + customerId);
    }

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