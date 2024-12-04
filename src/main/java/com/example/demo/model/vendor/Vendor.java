package com.example.demo.model.vendor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@NoArgsConstructor(force = true)
@Getter
@Setter
public class Vendor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    private String vendorName; // Optional, for logging/reporting
    private int ticketsPerRelease; // Tickets the vendor release per attempt
    private int releaseInterval; // Time between purchase attempts
    private Boolean active; // If customer is active in the system

    public Vendor(String vendorName, int ticketsPerRelease, int releaseInterval, Boolean active) {
        this.vendorName = vendorName;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.active = active;
    }
}