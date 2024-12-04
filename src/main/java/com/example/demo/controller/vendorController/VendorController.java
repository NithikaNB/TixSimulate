package com.example.demo.controller.vendorController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;

public interface VendorController {
    CommonResponse createVendor (Vendor vendor);
    CommonResponse startVendorTask (Long vendorId, Long ticketPoolId);
}
