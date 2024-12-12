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
@CrossOrigin(origins = "http://localhost:4200")
public class VendorControllerImpl implements VendorController {

    // ATTRIBUTES //
    @Autowired
    VendorService vendorService;


    // METHODS //
    // Method to create a vendor
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

}
