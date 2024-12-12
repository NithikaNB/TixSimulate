package com.example.demo.service.task;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskFactory {

    // This is a helper class that acts to communicate with both customer and vendor tasks with ticket pool

    // ATTRIBUTES //
    private final TicketPoolService ticketPoolService;
    private static final Logger logger = LoggerFactory.getLogger(TaskFactory.class);

    // CONSTRUCTOR //
    @Autowired
    public TaskFactory(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    // METHODS //

    // Method to create a customer task (Using CustomerTask class)
    public CustomerTask createCustomerTask(Customer customer, TicketPool ticketPool){
        logger.info("Customer " + customer.getCustomerName() + "'s task has been initiated");
        return new CustomerTask(customer, ticketPool, ticketPoolService);

    }

    /// Method to create a vendor task (Using VendorTask class)
    public VendorTask createVendorTask(Vendor vendor, TicketPool ticketPool){
        logger.info("Vendor " + vendor.getVendorName() + "'s task has been initiated");
        return new VendorTask(vendor, ticketPool, ticketPoolService);
    }
}
