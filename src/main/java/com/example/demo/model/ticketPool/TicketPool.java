package com.example.demo.model.ticketPool;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Data
@Entity
@NoArgsConstructor
public class TicketPool {

    //Attributes
    @Id //Auto generate an unique id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketPoolId;

    private String ticketPoolName;

    @Transient //Exclude from persistence; manage the tickets in memory
    private final Queue<Integer> availableTickets = new ConcurrentLinkedDeque<>(); // Thread-safe

    // Constructors


    public TicketPool(String ticketPoolName, int ticketCount) {
        this.ticketPoolName = ticketPoolName;
        addTickets(ticketCount);
    }

    // Method for adding a ticket to the ticket pool
    public void addTickets(int ticketCount){
        for (int i = 0; i < ticketCount; i++){
            availableTickets.add(1);
        }
    }

    // Method for removing a ticket from the ticket pool
    public boolean removeTickets(int ticketCount){
        if (ticketCount <= availableTickets.size()) {
            for (int i = 0; i < ticketCount; i++) {
                availableTickets.poll(); // Remove the head of the queue
            }
            return true;
        } else {
            return true;
        }
    }

    // Method to get the availableTickets
    public int getAvailableTickets(){
        return availableTickets.size();
    }

}
