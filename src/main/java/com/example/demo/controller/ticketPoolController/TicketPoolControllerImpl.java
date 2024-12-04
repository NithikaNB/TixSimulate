package com.example.demo.controller.ticketPoolController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.dto.response.ResponseConstants;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket-pools")
public class TicketPoolControllerImpl implements TicketPoolController{

    // ATTRIBUTES //
    private final TicketPoolService ticketPoolService;

    // CONSTRUCTOR //
    @Autowired
    public TicketPoolControllerImpl(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }


    // IMPLEMENTATION //
    @PostMapping("/create")
    @Override
    public CommonResponse createTicketPool(
            @RequestParam String ticketPoolName,
            @RequestParam int ticketCount) {

        try {
            ticketPoolService.createTicketPool(ticketPoolName, ticketCount);
            return new CommonResponse(ResponseConstants.SUCCESS, "Ticket pool: "+ ticketPoolName + ", has been created");
        } catch (Exception e){
            String message = "An error occurred: " + e.getMessage();
            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, message);

        }
    }

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



}
