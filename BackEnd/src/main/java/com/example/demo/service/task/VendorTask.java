package com.example.demo.service.task;

import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VendorTask implements Runnable {

    // ATTRIBUTES //
    private static final Logger logger = LoggerFactory.getLogger(VendorTask.class);
    private final Vendor vendor;
    private final TicketPool ticketPool;
    private final TicketPoolService ticketPoolService;
    private int releasedTickets = 0;

    // CONSTRUCTOR //
    public VendorTask(Vendor vendor, TicketPool ticketPool, TicketPoolService ticketPoolService) {
        this.vendor = vendor;
        this.ticketPool = ticketPool;
        this.ticketPoolService = ticketPoolService;
    }

    // run method 
    @Override
    public void run() {
        try {
            while (0 < ticketPoolService.getAvailableTickets(ticketPool.getTicketPoolName()) && releasedTickets < ticketPool.getMaxTicketCapacity()){
                synchronized (ticketPoolService){
                    // Add tickets to the ticket pool (Given ticket pool object)
                    int newTicketCount = ticketPoolService.getAvailableTickets(ticketPool.getTicketPoolName()) + vendor.getTicketsPerRelease();
                    if (newTicketCount < ticketPool.getMaxTicketCapacity()){
                        ticketPoolService.addTicket(ticketPool.getTicketPoolId(), vendor.getTicketsPerRelease());
                        releasedTickets += vendor.getTicketsPerRelease();
                        String message = vendor.getVendorName() + " added " + vendor.getTicketsPerRelease()
                                + " tickets to the pool: " + ticketPool.getTicketPoolName()
                                + " | Available Tickets: " + ticketPoolService.getAvailableTickets(ticketPool.getTicketPoolName());

                        // Logging the release of tickets
                        logger.info(message);
                    } else if (newTicketCount > ticketPool.getMaxTicketCapacity() && releasedTickets < ticketPool.getMaxTicketCapacity()){
                        String message = "Tickets haven't been added by vendor: " + vendor.getVendorName() + " because it exceeds the max ticket count of the ticket pool.";
                        logger.error(message);
                    }  else if (releasedTickets > ticketPool.getMaxTicketCapacity()) {
                        break;
                    }


                }

                // Wait for the specified release rate before the next release
                Thread.sleep(vendor.getReleaseInterval());

            }
        } catch (InterruptedException e){

            Thread.currentThread().interrupt();
            logger.error("Vendor task interrupted for vendor: " + vendor.getVendorName());
        } catch (Exception e){
            logger.error("Unexpected error occurred in VendorTask for vendor: " + vendor.getVendorName(), e);
        }
        logger.info(vendor.getVendorName() + " finished adding tickets. Tickets added: " + releasedTickets);
    }


}