package com.example.demo.controller.ticketPoolController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.dto.response.ResponseConstants;
import com.example.demo.service.customerService.CustomerService;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import com.example.demo.service.vendorService.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket-pools")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketPoolControllerImpl implements TicketPoolController{

    // ATTRIBUTES //
    private final TicketPoolService ticketPoolService;
    private final CustomerService customerService;
    private final VendorService vendorService;

    // CONSTRUCTOR //
    @Autowired
    public TicketPoolControllerImpl(TicketPoolService ticketPoolService, CustomerService customerService, VendorService vendorService) {
        this.ticketPoolService = ticketPoolService;
        this.customerService = customerService;
        this.vendorService = vendorService;
    }


    // METHODS //
    // Method to create a ticket pool
    @PostMapping("/create")
    @Override
    public CommonResponse createTicketPool(
            @RequestParam String ticketPoolName,
            @RequestParam int ticketCount,
            @RequestParam int maxTicketCapacity) {

        try {
            ticketPoolService.createTicketPool(ticketPoolName, ticketCount, maxTicketCapacity);
            return new CommonResponse(ResponseConstants.SUCCESS, "Ticket pool: "+ ticketPoolName + ", has been created");
        } catch (Exception e){
            String message = "An error occurred: " + e.getMessage();
            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, message);

        }
    }

    // Method to add ticket to a certain ticket pool
    @PostMapping("/{ticketPoolId}/add-ticket")
    @Override
    public CommonResponse addTicket(
            @RequestParam Long ticketPoolId,
            @RequestParam int ticketCount) {

        try {
            ticketPoolService.addTicket(ticketPoolId, ticketCount);
            String message = ticketCount + " tickets added successfully to pool ID: " + ticketPoolId;
            return new CommonResponse(ResponseConstants.SUCCESS, message);
        }catch (Exception e){
            String message = "An error occurred: " + e.getMessage();
            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, message);
        }

    }


    // Method to remove tickets form a certain ticket pool
    @PostMapping("/{ticketPoolId}/remove-ticket")
    @Override
    public CommonResponse removeTicket(Long ticketPoolId, int ticketCount) {
        try {
            ticketPoolService.addTicket(ticketPoolId, ticketCount);
            String message = ticketCount + " tickets removed successfully from pool ID: " + ticketPoolId;
            return new CommonResponse(ResponseConstants.SUCCESS, message);
        }catch (Exception e){
            String message = "An error occurred: " + e.getMessage();
            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, message);
        }
    }


    // Method to get the available ticket count of a certain ticket pool
    @GetMapping("/availabletickets/{ticketPoolName}")
    @Override
    public Integer getAvailableTickets(@PathVariable String ticketPoolName) {
        Integer IntegerValue = ticketPoolService.getAvailableTickets(ticketPoolName);
        return IntegerValue;

    }
}
