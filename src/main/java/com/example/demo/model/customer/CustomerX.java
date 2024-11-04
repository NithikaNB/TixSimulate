package com.example.demo.model.customer;

import com.example.demo.service.customerService.CustomerService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerX implements Runnable {
    private static final Logger logger = Logger.getLogger(CustomerX.class.getName());

    private final int customerId;
    private final int retrievalInterval;
    private final int maxTicketsToPurchase;
    private final int customerRetrievalRate;
    private final CustomerService customerService;

    public CustomerX(int customerId, int retrievalInterval, int maxTicketsToPurchase, int customerRetrievalRate, CustomerService customerService) {
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