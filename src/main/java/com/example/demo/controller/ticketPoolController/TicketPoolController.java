package com.example.demo.controller.ticketPoolController;

import com.example.demo.dto.response.CommonResponse;

public interface TicketPoolController {
    CommonResponse createTicketPool(String ticketPoolName, int ticketCount);
    CommonResponse addTicket(Long ticketPoolId, int ticketCount);
    CommonResponse removeTicket(Long ticketPoolId, int ticketCount);
    CommonResponse sampleTask();
}
