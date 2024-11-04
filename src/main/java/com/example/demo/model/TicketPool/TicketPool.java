package com.example.demo.model.TicketPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketPool {
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    private final List<Integer> tickets;
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.tickets = Collections.synchronizedList(new ArrayList<>());
        this.maxCapacity = maxCapacity;
    }

    // Synchronized method for vendors to add tickets to the pool
    public synchronized void addTickets(int ticketsToAdd) {
        while (tickets.size() + ticketsToAdd > maxCapacity) {
            logger.info("Cannot add tickets: Exceeds maximum capacity. Vendor is waiting...");
            try {
                wait(); // Wait until there's space in the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.SEVERE, "Vendor thread interrupted while waiting", e);
                return;
            }
        }
        for (int i = 0; i < ticketsToAdd; i++) {
            tickets.add(1); // Add each ticket
        }
        logger.info("Added " + ticketsToAdd + " tickets. Current pool size: " + tickets.size());
        notifyAll(); // Notify waiting customer threads that tickets are available
    }

    // Synchronized method for customers to remove a ticket from the pool
    public synchronized boolean removeTicket(int customerRetrievalRate) {
        while (tickets.size() < customerRetrievalRate) {
            logger.info("Not enough tickets available. Customer waiting for tickets...");
            try {
                wait(); // Wait for tickets to be added if there aren’t enough
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.SEVERE, "Customer thread interrupted while waiting", e);
                return false;
            }
        }
        for (int i = 0; i < customerRetrievalRate; i++) {
            tickets.remove(0); // Remove one ticket at a time
        }
        logger.info("Customer purchased " + customerRetrievalRate + " tickets. Remaining tickets: " + tickets.size());
        notifyAll(); // Notify vendors if space is now available
        return true;
    }
}
