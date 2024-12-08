package com.example.demo.model.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@Data
public class Configuration implements Serializable {
    // ATTRIBUTES //
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long configurationId;

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Static counter to keep track of the last used ID
    private static long idCounter = 0;

    // CONSTRUCTOR //
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.configurationId = generateId();
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Generate a new sequential ID
    private static long generateId() {
        return ++idCounter;  // Increment and return the new ID
    }


}
