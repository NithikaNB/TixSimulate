package com.example.demo.model.Customer;

import com.example.demo.service.CustomerService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    private final int customerId;
    private final int retrievalInterval;
    private final int maxTicketsToPurchase;
    private final int customerRetrievalRate;
    private final CustomerService customerService;

    public Customer(int customerId, int retrievalInterval, int maxTicketsToPurchase, int customerRetrievalRate, CustomerService customerService) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.maxTicketsToPurchase = maxTicketsToPurchase;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        int ticketsPurchased = 0;
        try {
            while (ticketsPurchased < maxTicketsToPurchase) {
                boolean purchased = customerService.attemptPurchase(customerId, customerRetrievalRate);
                if (purchased) {
                    ticketsPurchased += customerRetrievalRate;
                    logger.info("Customer " + customerId + " total tickets purchased: " + ticketsPurchased);
                }
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Customer " + customerId + " was interrupted.", e);
            Thread.currentThread().interrupt();
        } finally {
            logger.info("Customer " + customerId + " finished purchasing tickets.");
        }
    }
}