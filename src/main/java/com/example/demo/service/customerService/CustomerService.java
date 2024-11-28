package com.example.demo.service.customerService;

import com.example.demo.model.customer.Customer;

public interface CustomerService {
    void createCustomer(Customer customer);
    void executeTask(Long customerId);
}
