package com.example.demo.service.customerService;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CustomerServiceImpl implements CustomerService{
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

//    private final CustomerRepository customerRepository;

    // Creates a fixed thread pool with 10 threads to handle concurrent tasks efficiently.
    // The ExecutorService manages the lifecycle of the threads and executes tasks asynchronously.
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private TicketPoolService ticketPoolService;


    public CustomerServiceImpl(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    @Override
    public void createCustomer(Customer customer1){
        Customer customer = new Customer();
        customer.setCustomerName(customer1.getCustomerName());
        customer.setRetrievalInterval(customer1.getRetrievalInterval());
        customer.setTicketsRequested(customer1.getTicketsRequested());
        customer.setTicketsPurchased(customer1.getTicketsPurchased());
        logger.info("Customer " + customer.getCustomerName() + " has been created!");
//        System.out.println("Customer " + customer.getCustomerName() + " has been created!");
    }

    @Override
    public void startCustomerTask(Long customerId) {
        try {
            // Create a Customer object
            Customer customer1 = new Customer("Customer1", 10, 5, 0, true);
            logger.info("Customer object created: " + customer1.getCustomerName());

            // Create a Ticket Pool using the Service Class
            String ticketPoolName = "Movie";  // Name of the ticket pool
            int ticketCount = 100;           // Total tickets available
            ticketPoolService.createTicketPool(ticketPoolName, ticketCount);
            logger.info("Ticket pool created: " + ticketPoolName + " with " + ticketCount + " tickets");

            // Retrieve the Ticket Pool object
            TicketPool ticketPool = ticketPoolService.getTicketPoolByName(ticketPoolName);

            // Create a CustomerTask and submit it for execution
            CustomerTask customerTask = new CustomerTask(customer1, ticketPool, ticketPoolService);
            logger.info("CustomerTask object created for execution");
            executorService.submit(customerTask);
            logger.info("Task submitted for execution...");
        } catch (Exception e) {
            // Handle and log any exceptions
            String message = "Error occurred: " + e.getMessage();
            System.out.println(message);
            logger.error(message);
        }
    }

}