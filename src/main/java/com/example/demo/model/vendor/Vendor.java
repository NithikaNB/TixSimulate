package com.example.demo.model.vendor;

import com.example.demo.service.vendorService.VendorService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class Vendor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;


}