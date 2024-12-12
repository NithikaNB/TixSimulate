package com.example.demo.model.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;
import lombok.*;

//@Entity
@NoArgsConstructor(force = true)
@Getter
@Setter
//@Builder
public class Customer{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;
    private String customerName; // Optional, for logging/reporting
//    private boolean priority; // True if VIP
    private int retrievalInterval; // Time between purchase attempts
    private int ticketsRequested; // Tickets the customer wants to buy
    private int ticketsPurchased; // Tracks tickets successfully purchased



    // CONSTRUCTOR //


    public Customer(String customerName, int retrievalInterval, int ticketsRequested, int ticketsPurchased) {
        this.customerName = customerName;
        this.retrievalInterval = retrievalInterval;
        this.ticketsRequested = ticketsRequested;
        this.ticketsPurchased = ticketsPurchased;
    }
}
