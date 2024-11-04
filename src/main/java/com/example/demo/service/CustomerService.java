package com.example.demo.service;

import com.example.demo.config.Configuration;

import java.util.logging.Logger;

public class CustomerService {
    private static final Logger logger = Logger.getLogger(CustomerService.class.getName());

    private final Configuration.TicketPool ticketPool;

    public CustomerService(Configuration.TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    // Method to attempt ticket purchase
    public boolean attemptPurchase(int customerId, int ticketsToBuy) {
        boolean purchased = ticketPool.removeTicket(ticketsToBuy);
        if (purchased) {
            logger.info("Customer " + customerId + " successfully purchased " + ticketsToBuy + " tickets.");
        } else {
            logger.info("Customer " + customerId + " failed to purchase " + ticketsToBuy + " tickets (pool empty or insufficient tickets).");
        }
        return purchased;
    }
}