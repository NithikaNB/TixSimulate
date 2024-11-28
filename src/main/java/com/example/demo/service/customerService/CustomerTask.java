package com.example.demo.service.customerService;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@AllArgsConstructor
public class CustomerTask implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(CustomerTask.class);
    private final Customer customer;
    private final TicketPool ticketPool;


    @Override
    public void run() {

            try {
                while (customer.getTicketsRequested() <= ticketPool.getAvailableTickets()){
                    synchronized (ticketPool){
                        if(customer.getTicketsRequested() <= ticketPool.getAvailableTickets()){
                            //When sufficient tickets available
                            ticketPool.removeTickets(customer.getTicketsRequested());
                            int ticketsPurchased = customer.getTicketsPurchased() + customer.getTicketsRequested();
                            customer.setTicketsPurchased(ticketsPurchased);
                            System.out.println(customer.getCustomerName() + " successfully purchased " + customer.getTicketsRequested() + " tickets");
                            logger.info(customer.getCustomerName() + " successfully purchased " + customer.getTicketsRequested() + " tickets");
                        } else {
                            //When tickets are not sufficient
                            System.out.println(customer.getCustomerName() + " failed to purchase tickets. Retrying...");
                            logger.error(customer.getCustomerName() + " failed to purchase tickets. Retrying...");
                        }

                    }
                    // Wait for the specified retrieval interval before retrying
                    Thread.sleep(customer.getRetrievalInterval());
                }


            }catch (Exception e){
                Thread.currentThread().interrupt();
                System.err.println(customer.getCustomerName() + " was interrupted!");
                logger.error(customer.getCustomerName() + " was interrupted!" + e.getMessage() + " occurred.");

            }

        // Final log for the customer's activity
        System.out.println(customer.getCustomerName() + " finished ticket purchasing. Tickets purchased: " + customer.getTicketsPurchased());
        logger.info(customer.getCustomerName() + " finished ticket purchasing. Tickets purchased: " + customer.getTicketsPurchased());
    }
}
