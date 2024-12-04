package com.example.demo.model.ticketPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public class TicketPool2 {
    private final List<Integer> tickets = Collections.synchronizedList(new ArrayList<>()); // Thread-safe list
    private static final Logger logger = Logger.getLogger(TicketPool2.class.getName());


    public TicketPool2(int ticketCount) {
        addTicket(ticketCount);
        logger.info("TicketPool Constructor has been initiated");
    }

    // Synchronized method for adding a ticket to the pool
    public synchronized void addTicket(int ticketCount) {
        for (int i =0; i < ticketCount ; i++) {
            tickets.add(1);
        }
        String message = ticketCount + " tickets have been added to the ticketPool";
        logger.info(message);
    }
    public synchronized List<Integer> getTickets(){
        return tickets;
    }
    public synchronized void removeTickets(int ticketCount){
        if (ticketCount <= tickets.size()) {
            for (int i = 0; i < ticketCount; i++) {
                tickets.remove(0);
            }

            String message = ticketCount + " tickets have been removed from the ticketPool";
            logger.info(message);
        }else{
            System.out.println("TicketCount Exceed.");
            logger.info("Requested number of tickets exceed the existing number of tickets");

        }


    }

    public synchronized int getAvailableTickets(){
        return tickets.size();
    }

    public synchronized boolean isRequiredTicketsWithinCapacity (int requiredTickets){
        if (requiredTickets <= tickets.size()){
            return true;
        }else {
            return false;        }

    }


}