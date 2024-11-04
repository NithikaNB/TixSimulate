package com.example.demo.model.vendor;

import com.example.demo.service.vendorService.VendorService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());

    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    private final VendorService vendorService;

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, VendorService vendorService) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.vendorService = vendorService;
    }

    @Override
    public void run() {
        try {
            while (true) {
                vendorService.releaseTickets(vendorId, ticketsPerRelease);
                Thread.sleep(releaseInterval);
            }
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Vendor " + vendorId + " was interrupted.", e);
            Thread.currentThread().interrupt();
        }
    }
}