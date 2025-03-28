package com.example.demo.model.configuration;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

//@Entity
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



    // CONSTRUCTOR //
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }



}
