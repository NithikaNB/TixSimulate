package com.example.demo.controller.customerController;

import com.example.demo.model.customer.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerController {
    ResponseEntity<String> createCustomer(Customer customer);
    ResponseEntity<String> startCustomerTask(Long customerId);
}
