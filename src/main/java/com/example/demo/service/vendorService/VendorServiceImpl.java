package com.example.demo.service.vendorService;

import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Service
public class VendorServiceImpl implements VendorService {

    // ATTRIBUTES //
    private static final Logger logger = Logger.getLogger(VendorServiceImpl.class.getName());
    private final Map<Long, Vendor> vendorMap = new ConcurrentHashMap<>();
    private final TicketPoolService ticketPoolService;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    // CONSTRUCTOR //
    @Autowired
    public VendorServiceImpl(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    // IMPLEMENTATION //
    @Override
    public void createVendor(Vendor vendor) {

        // Check whether the releaseInterval and ticketPerRelease is greater than zero
        if (vendor.getReleaseInterval() <= 0 || vendor.getTicketsPerRelease() <=0){
            throw new IllegalArgumentException("Ticket release rate and tickets per release must be greater than zero.");
        }

        // Creates the vendor object
        long vendorId = vendor.getVendorId();

        // Save the vendor with according vendorId inside a hashmap
        vendorMap.put(vendorId, vendor);
        logger.info("Vendor created: " + vendor.getVendorName() + " with ID: " + vendor.getVendorId());
    }

    @Override
    public void startVendorTask(Long vendorId, TicketPool ticketPool) {

        // Get the vendor object stored inside the hashmap
        Vendor vendor = vendorMap.get(vendorId);

        // Check whether there exists a vendor object according to the provided vendorId
        if (vendor == null){
            throw new IllegalArgumentException("Vendor not found for ID: " + vendorId);
        }

        // Pass vendor, ticketPool, ticketPoolService objects into VendorTask and create a vendorTask object
        VendorTask vendorTask = new VendorTask(vendor, ticketPool, ticketPoolService);
        executorService.submit(vendorTask);
        logger.info("Vendor task started for vendor ID: " + vendorId);


    }
}
