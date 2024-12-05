package com.example.demo.repository.vendorRepository;

import com.example.demo.model.vendor.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository <Vendor, Long> {
    // Can be implemented later when implementing a database
}
