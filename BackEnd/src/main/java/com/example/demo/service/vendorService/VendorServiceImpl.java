package com.example.demo.service.vendorService;

import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.task.TaskFactory;
import com.example.demo.service.task.VendorTask;
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
    private final TaskFactory taskFactory;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    // CONSTRUCTOR //
    @Autowired
    public VendorServiceImpl(TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
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
        VendorTask vendorTask = taskFactory.createVendorTask(vendor, ticketPool);
        executorService.submit(vendorTask);
        logger.info("Vendor task started for vendor ID: " + vendorId);


    }

    @Override
    public Vendor findVendorByName(String vendorName) {
        // Check whether the vendorName is empty
        if (vendorName == null || vendorName.trim().isEmpty()) {
            logger.warning("Invalid vendor name provided for search.");
            throw new IllegalArgumentException("Vendor name must not be null or empty.");
        }

        // Iterating each vendor object in the hashmap to find a match
        for (Vendor vendor : vendorMap.values()) {
            if (vendor.getVendorName().equalsIgnoreCase(vendorName)) {
                logger.info("Vendor found: " + vendorName);
                return vendor;
            }
        }

        // When fails to find a match
        logger.warning("Vendor not found with name: " + vendorName);
        throw new IllegalArgumentException("Vendor not found with name: " + vendorName);
    }

    @Override
    public Vendor findVendorById(Long vendorId) {
        // Check whether the vendorId is empty
        if (vendorId == null) {
            logger.warning("Invalid vendor ID provided for search.");
            throw new IllegalArgumentException("Vendor ID must not be null.");
        }

        Vendor vendor = vendorMap.get(vendorId);
        if (vendor != null) {
            logger.info("Vendor found with ID: " + vendorId);
            return vendor;
        }

        // When fails to find a match
        logger.warning("Vendor not found with ID: " + vendorId);
        throw new IllegalArgumentException("Vendor not found with ID: " + vendorId);
    }
}
