package com.example.demo.service.customerService;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;

public interface CustomerService {
    void createCustomer(Customer customer);
    void startCustomerTask(Long customerId, TicketPool ticketPool);
    Customer findCustomerByName(String customerName);
    Customer findCustomerById(Long customerId);
}
