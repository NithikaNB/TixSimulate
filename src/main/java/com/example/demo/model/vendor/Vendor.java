package com.example.demo.model.vendor;

import com.example.demo.service.vendorService.VendorService;
import lombok.AllArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class Vendor implements Runnable {

    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;



    @Override
    public void run() {


    }
}