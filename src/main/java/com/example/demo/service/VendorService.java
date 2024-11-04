package com.example.demo.service;

import com.example.demo.config.Configuration;

import java.util.logging.Logger;

public class VendorService {
    private static final Logger logger = Logger.getLogger(VendorService.class.getName());

    private final Configuration.TicketPool ticketPool;

    public VendorService(Configuration.TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    // Method to release tickets to the pool
    public void releaseTickets(int vendorId, int ticketsToAdd) {
        ticketPool.addTickets(ticketsToAdd);
        logger.info("Vendor " + vendorId + " added " + ticketsToAdd + " tickets to the pool.");
    }
}