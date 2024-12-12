package com.example.demo.controller.customerController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.dto.response.ResponseConstants;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.service.customerService.CustomerService;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerControllerImpl implements CustomerController {

    // ATTRIBUTES //
    @Autowired
    CustomerService customerService;


    // METHODS //

    // Method to create customers
    @PostMapping
    public CommonResponse createCustomer(@RequestBody Customer customer){
        try {
            customerService.createCustomer(customer);
            String message = "Customer " + customer.getCustomerName() + " created successfully!";
            return new CommonResponse(ResponseConstants.SUCCESS, message);
        }catch (Exception e){
            String message = "An error occurred: " + e.getMessage();
            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, message);
        }

    }

}
