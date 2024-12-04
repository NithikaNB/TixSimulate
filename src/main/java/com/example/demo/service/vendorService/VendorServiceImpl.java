package com.example.demo.service.vendorService;

import com.example.demo.model.vendor.Vendor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Service
public class VendorServiceImpl implements VendorService {

    private static final Logger logger = Logger.getLogger(VendorServiceImpl.class.getName());
    private final Map<Long, Vendor> vendorMap = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void createVendor(String vendorName, int releaseInterval, int ticketPerRelease) {
        if (releaseInterval <= 0 || ticketPerRelease <=0){
            throw new IllegalArgumentException("Ticket release rate and tickets per release must be greater than zero.");
        }
        Vendor vendor = new Vendor(vendorName, ticketPerRelease, releaseInterval, true);
        long vendorId = vendor.getVendorId();

        vendorMap.put(vendorId, vendor);
        logger.info("Vendor created: " + vendorName + " with ID: " + vendorId);
    }

    @Override
    public void startTask(Long vendorId) {

    }
}
