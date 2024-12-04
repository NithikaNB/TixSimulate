package com.example.demo.controller.customerController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.dto.response.ResponseConstants;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.service.customerService.CustomerService;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;
    private final TicketPoolService ticketPoolService;

    @Autowired
    public CustomerControllerImpl(CustomerService customerService, TicketPoolService ticketPoolService) {
        this.customerService = customerService;
        this.ticketPoolService = ticketPoolService;
    }

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

    @PostMapping("/{customerId}/start-task")
    public CommonResponse startCustomerTask(@PathVariable Long customerId, @PathVariable Long ticketPoolId) {
        TicketPool ticketPool = ticketPoolService.getTicketPoolById(ticketPoolId);

        try {
            customerService.startCustomerTask(customerId, ticketPool);
            String message = "Customer task started successfully!";
            return new CommonResponse(ResponseConstants.SUCCESS, message);
        }catch (Exception e){
            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, e.getMessage());
        }

    }
}
