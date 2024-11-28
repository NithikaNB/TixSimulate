package com.example.demo.controller.customerController;

import com.example.demo.model.customer.Customer;
import com.example.demo.service.customerService.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer){
        customerService.createCustomer(customer);
        return ResponseEntity.ok("Customer " + customer.getCustomerName() + " created successfully!");

    }

        @PostMapping("/{customerId}/start-task")
        public ResponseEntity<String> startCustomerTask(@PathVariable Long customerId){
            customerService.executeTask(customerId);
            return ResponseEntity.ok("Customer task started successfully!");
        }
}
