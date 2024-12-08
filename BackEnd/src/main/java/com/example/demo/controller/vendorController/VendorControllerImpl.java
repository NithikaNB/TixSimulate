package com.example.demo.controller.vendorController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.dto.response.ResponseConstants;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import com.example.demo.service.vendorService.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
public class VendorControllerImpl implements VendorController {

    // ATTRIBUTES //
    @Autowired
    VendorService vendorService;
//    private final TicketPoolService ticketPoolService;


    // CONSTRUCTOR //

    // IMPLEMENTATION //
    @PostMapping
    @Override
    public CommonResponse createVendor(@RequestBody Vendor vendor) {
        try {
            vendorService.createVendor(vendor);
            String message = "Vendor " + vendor.getVendorName() + " created successfully!";
            return new CommonResponse(ResponseConstants.SUCCESS, message);
        }catch (Exception e){
            String message = "An error occurred: " + e.getMessage();
            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, message);
        }
    }

//    @PostMapping("/{vendorId}/start-task")
//    @Override
//    public CommonResponse startVendorTask(@PathVariable Long vendorId, @PathVariable Long ticketPoolId) {
//        TicketPool ticketPool = ticketPoolService.getTicketPoolById(ticketPoolId);
//        try {
//            vendorService.startVendorTask(vendorId, ticketPool);
//            String message = "Customer task started successfully!";
//            return new CommonResponse(ResponseConstants.SUCCESS, message);
//        }catch (Exception e){
//            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, e.getMessage());
//        }
//
//    }
}
