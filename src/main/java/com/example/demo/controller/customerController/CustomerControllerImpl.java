package com.example.demo.controller.customerController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.dto.response.ResponseConstants;
import com.example.demo.model.customer.Customer;
import com.example.demo.service.customerService.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CommonResponse createCustomer(@RequestBody Customer customer){
        customerService.createCustomer(customer);
        String message = "Customer " + customer.getCustomerName() + " created successfully!";
        return new CommonResponse(ResponseConstants.SUCCESS, message);
    }

    @PostMapping("/{customerId}/start-task")
    public CommonResponse startCustomerTask(@PathVariable Long customerId) {
        customerService.startCustomerTask(customerId);
        String message = "Customer task started successfully!";
        return new CommonResponse(ResponseConstants.SUCCESS, message);
    }
}
