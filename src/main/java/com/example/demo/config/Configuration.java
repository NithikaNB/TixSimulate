package com.example.demo.config;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {
    private final Gson gson = new Gson();
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        setTotalTickets(totalTickets);
        setTicketReleaseRate(ticketReleaseRate);
        setCustomerRetrievalRate(customerRetrievalRate);
        setMaxTicketCapacity(maxTicketCapacity);
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setTotalTickets(int totalTickets) {
        if (totalTickets < 0) {
            throw new IllegalArgumentException("Total tickets cannot be negative.");
        }
        if (totalTickets > maxTicketCapacity) {
            throw new IllegalArgumentException("Total tickets cannot exceed max ticket capacity.");
        }
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        if (ticketReleaseRate <= 0) {
            throw new IllegalArgumentException("Ticket release rate must be greater than zero.");
        }
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        if (customerRetrievalRate <= 0) {
            throw new IllegalArgumentException("Customer retrieval rate must be greater than zero.");
        }
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        if (maxTicketCapacity <= 0) {
            throw new IllegalArgumentException("Max ticket capacity must be greater than zero.");
        }
        if (totalTickets > maxTicketCapacity) {
            throw new IllegalArgumentException("Total tickets cannot exceed max ticket capacity.");
        }
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void saveToJson(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
            System.out.println("Configuration saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }

    public void loadFromJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Configuration loadedConfig = gson.fromJson(reader, Configuration.class);
            this.totalTickets = loadedConfig.totalTickets;
            this.ticketReleaseRate = loadedConfig.ticketReleaseRate;
            this.customerRetrievalRate = loadedConfig.customerRetrievalRate;
            this.maxTicketCapacity = loadedConfig.maxTicketCapacity;
        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }

    public static class TicketPool {
        private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

        private final List<Integer> tickets;
        private final int maxCapacity;


        public TicketPool(int maxCapacity) {
            this.tickets = Collections.synchronizedList(new ArrayList<>());
            this.maxCapacity = maxCapacity;
        }

        public int TicketSize() {
            return
                    tickets.size();
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
}