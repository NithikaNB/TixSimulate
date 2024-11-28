package com.example.demo.service.customerService;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.repository.customerRepository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CustomerServiceImpl implements CustomerService{
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

//    private final CustomerRepository customerRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

//    public CustomerServiceImpl(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }

    @Override
    public void createCustomer(Customer customer1){
        Customer customer = new Customer();
        customer.setCustomerName(customer1.getCustomerName());
        customer.setRetrievalInterval(customer1.getRetrievalInterval());
        customer.setTicketsRequested(customer1.getTicketsRequested());
        customer.setTicketsPurchased(customer1.getTicketsPurchased());
        System.out.println("Customer " + customer.getCustomerName() + " has been created!");
    }

    @Override
    public void executeTask(Long customerId) {
//      Customer customer = customerRepository.findById(customerId);
        Customer customer1 = new Customer(1, "Customer1", 10, 5, 0, true);
        TicketPool ticketPool = new TicketPool(100);
        CustomerTask customerTask = new CustomerTask(customer1, ticketPool);
        System.out.println("Objects created");
        executorService.submit(customerTask);
        System.out.println("Tasks executed");
    }
}