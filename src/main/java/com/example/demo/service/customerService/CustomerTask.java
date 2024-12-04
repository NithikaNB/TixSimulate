package com.example.demo.service.customerService;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import com.example.demo.service.ticketPoolService.TicketPoolServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerTask implements Runnable{
    // ATTRIBUTES //
    private static final Logger logger = LoggerFactory.getLogger(CustomerTask.class);
    private final Customer customer;
    private final TicketPool ticketPool;

    private TicketPoolService ticketPoolService;

    // CONSTRUCTOR //
    public CustomerTask(Customer customer, TicketPool ticketPool, TicketPoolService ticketPoolService) {
        this.customer = customer;
        this.ticketPool = ticketPool;
        this.ticketPoolService = ticketPoolService;
    }

    @Override
    public void run() {
        try {
            // Validate that the requested tickets are available
            while (customer.getTicketsRequested() <= ticketPoolService.getAvailableTickets(ticketPool.getTicketPoolName())) {
                synchronized (ticketPoolService) {
                    // Re-check after acquiring the lock
                    if (customer.getTicketsRequested() <= ticketPoolService.getAvailableTickets(ticketPool.getTicketPoolName())) {
                        // Sufficient tickets are available
                        ticketPoolService.removeTicket(ticketPool.getTicketPoolId(), customer.getTicketsRequested());

                        // Update customer's purchased tickets
                        int ticketsPurchased = customer.getTicketsPurchased() + customer.getTicketsRequested();
                        customer.setTicketsPurchased(ticketsPurchased);

                        // Log success
//                        System.out.println(customer.getCustomerName() + " successfully purchased " + customer.getTicketsRequested() + " tickets.");
                        logger.info(customer.getCustomerName() + " successfully purchased " + customer.getTicketsRequested() + " tickets.");
                    } else {
                        // Insufficient tickets
//                        System.out.println(customer.getCustomerName() + " failed to purchase tickets. Retrying...");
                        logger.warn(customer.getCustomerName() + " failed to purchase tickets. Retrying...");
                    }
                }
                // Wait for the retrieval interval before retrying
                Thread.sleep(customer.getRetrievalInterval());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
//            System.err.println(customer.getCustomerName() + " was interrupted!");
            logger.error(customer.getCustomerName() + " was interrupted! " + e.getMessage());
        } catch (Exception e) {
//            System.err.println("An unexpected error occurred for " + customer.getCustomerName() + ": " + e.getMessage());
            logger.error("An unexpected error occurred for " + customer.getCustomerName() + ": " + e.getMessage());
        }

        // Final log for the customer's activity
//        System.out.println(customer.getCustomerName() + " finished ticket purchasing. Tickets purchased: " + customer.getTicketsPurchased());
        logger.info(customer.getCustomerName() + " finished ticket purchasing. Tickets purchased: " + customer.getTicketsPurchased());
    }

}
