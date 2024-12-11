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
    private final TicketPoolService ticketPoolService;

    private static final Logger logger = LoggerFactory.getLogger(TaskFactory.class);

    @Autowired
    public TaskFactory(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    public CustomerTask createCustomerTask(Customer customer, TicketPool ticketPool){
        logger.info("Customer " + customer.getCustomerName() + "'s task has been initiated");
        return new CustomerTask(customer, ticketPool, ticketPoolService);

    }

    public VendorTask createVendorTask(Vendor vendor, TicketPool ticketPool){
        logger.info("Vendor " + vendor.getVendorName() + "'s task has been initiated");
        return new VendorTask(vendor, ticketPool, ticketPoolService);
    }
}
