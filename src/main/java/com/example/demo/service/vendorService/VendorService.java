package com.example.demo.service.vendorService;

public interface VendorService {

    void createVendor (String vendorName, int releaseInterval, int ticketPerRelease);
    void startTask (Long vendorId);
}
