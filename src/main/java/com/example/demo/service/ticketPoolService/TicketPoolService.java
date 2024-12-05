package com.example.demo.service.ticketPoolService;


import com.example.demo.model.ticketPool.TicketPool;

public interface TicketPoolService {
    void createTicketPool(String ticketPoolName, int ticketCount);
    void addTicket(Long ticketPoolId, int ticketCount);
    void removeTicket(Long ticketPoolId, int ticketCount);
    TicketPool getTicketPoolById(Long ticketPoolId);
    TicketPool getTicketPoolByName(String ticketPoolName);
    int getAvailableTickets(String ticketPoolName);
    void sampleTask();
}
