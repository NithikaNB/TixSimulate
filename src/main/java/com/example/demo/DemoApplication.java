package com.example.demo;

import com.example.demo.config.Configuration;
import com.example.demo.model.customer.CustomerX;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.customerService.CustomerService;
import com.example.demo.service.vendorService.VendorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		initTicketingSystem();
	}

	public static void initTicketingSystem() {
		Configuration.TicketPool ticketPool = new Configuration.TicketPool(100);

		// Create services
		CustomerService customerService = new CustomerService(ticketPool);
		VendorService vendorService = new VendorService(ticketPool);

		// Create and start vendor threads
		Vendor vendor1 = new Vendor(1, 10, 2000, vendorService);
		Vendor vendor2 = new Vendor(2, 5, 3000, vendorService);
		new Thread(vendor1).start();
		new Thread(vendor2).start();

		// Create and start customer threads
		CustomerX customer1 = new CustomerX(1, 1000, 5, 1, customerService);
		CustomerX customer2 = new CustomerX(2, 1500, 5, 1, customerService);
		new Thread(customer1).start();
		new Thread(customer2).start();
	}
}