package com.example.demo.controller.customerController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.model.customer.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerController {
    CommonResponse createCustomer(Customer customer);
//
}
