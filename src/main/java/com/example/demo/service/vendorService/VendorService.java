package com.example.demo.service.vendorService;

import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;

public interface VendorService {

    void createVendor (Vendor vendor);
    void startVendorTask(Long vendorId, TicketPool ticketPool);
    Vendor findVendorByName(String vendorName);
    Vendor findVendorById(Long vendorId);
}
