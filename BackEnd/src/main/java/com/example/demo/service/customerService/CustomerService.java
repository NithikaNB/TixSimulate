package com.example.demo.service.customerService;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;

public interface CustomerService {
    void createCustomer(Customer customer);
    Customer findCustomerByName(String customerName);
    Customer findCustomerById(Long customerId);
}
