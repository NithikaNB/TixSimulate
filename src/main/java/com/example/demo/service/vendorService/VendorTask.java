package com.example.demo.service.vendorService;

import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.table.TableRowSorter;

public class VendorTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(VendorTask.class);
    private final Vendor vendor;
    private final TicketPool ticketPool;
    private final TicketPoolService ticketPoolService;

    public VendorTask(Vendor vendor, TicketPool ticketPool, TicketPoolService ticketPoolService) {
        this.vendor = vendor;
        this.ticketPool = ticketPool;
        this.ticketPoolService = ticketPoolService;
    }

    @Override
    public void run() {
        try {
            while (true){
                synchronized (ticketPoolService){
                    // Add tickets to the ticket pool (Given ticket pool object)
                    ticketPoolService.addTicket(ticketPool.getTicketPoolId(), vendor.getTicketsPerRelease());
                    String message = vendor.getVendorName() + " added " + vendor.getTicketsPerRelease()
                            + " tickets to the pool: " + ticketPool.getTicketPoolName();

                    // Logging the release of tickets
                    logger.info(message);

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

    }


}
