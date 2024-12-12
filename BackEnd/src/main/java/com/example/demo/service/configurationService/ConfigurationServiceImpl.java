package com.example.demo.service.configurationService;

import com.example.demo.model.configuration.Configuration;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.ticketPool.TicketPool;
import com.example.demo.model.vendor.Vendor;
import com.example.demo.service.task.CustomerTask;
import com.example.demo.service.task.TaskFactory;
import com.example.demo.service.task.VendorTask;
import com.example.demo.service.customerService.CustomerServiceImpl;
import com.example.demo.service.ticketPoolService.TicketPoolService;
import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    // ATTRIBUTES //

    private final TaskFactory taskFactory;
    private final TicketPoolService ticketPoolService;
    private final Map<Long, Configuration> configurationMap = new LinkedHashMap<>();
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private static long idCounter = 0;
    private final SimpMessagingTemplate messagingTemplate;
    Gson gson = new Gson();



    // CONSTRUCTOR //
    @Autowired
    public ConfigurationServiceImpl(TaskFactory taskFactory, TicketPoolService ticketPoolService, SimpMessagingTemplate messagingTemplate) {
        this.taskFactory = taskFactory;
        this.ticketPoolService = ticketPoolService;
        this.messagingTemplate = messagingTemplate;
    }


    // METHODS //

    // Method for create configurations (With proper valdiation)
    @Override
    public void createConfig(Configuration configuration) {


        // Validation
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration wasn't received properly.");
        }
        if (configuration.getTotalTickets() <= 0) {
            throw new IllegalArgumentException("Total tickets must be greater than 0.");
        }
        if (configuration.getTicketReleaseRate() <= 0) {
            throw new IllegalArgumentException("Ticket release rate must be greater than 0.");
        }
        if (configuration.getCustomerRetrievalRate() <= 0) {
            throw new IllegalArgumentException("Customer retrieval rate must be greater than 0.");
        }
        if (configuration.getMaxTicketCapacity() <= 0) {
            throw new IllegalArgumentException("Maximum ticket capacity must be greater than 0.");
        }
        if (configuration.getMaxTicketCapacity() < configuration.getTotalTickets()) {
            throw new IllegalArgumentException("Total tickets should not exceed maxTicketCapacity");
        }

        // Get the configurationId
        long configurationId = idCounter;
        ++idCounter;
        configuration.setConfigurationId(configurationId);

        // Save to the HashMap and in a Json file
        configurationMap.put(configurationId, configuration);
        saveToJson("config.json", configuration);

        logger.info("Configuration saved with ID: {}", configuration.getConfigurationId());

        // Creates an empty ticket pool with the name of "movie" for simulation purpose
        if(ticketPoolService.getTicketPoolByName("movie") == null) {
            ticketPoolService.createTicketPool("movie", configuration.getTotalTickets(), configuration.getMaxTicketCapacity());
        }
    }


    // Method with sample threads for the simulation purpose
    @Override
    public void runTask() {


        if (running.compareAndSet(false,true)){
            // LOGIC to Start
            logger.info("Starting tasks...");

            // Reinitialize the ExecutorService if it's terminated
            if (executorService == null || executorService.isShutdown() || executorService.isTerminated()) {
                executorService = Executors.newCachedThreadPool();
            }

            // Loading the latest config
            Configuration configuration = getLatestConfig();


            // Creating a sample ticketPool (If not being created) or get the existing ticketPool object and assign the values according to configuration
            // Since there is an init method to create a sample ticket pool in TicketPoolServiceImpl, this will check whether it's been created and creates if not one is created
            if (ticketPoolService.getTicketPoolByName("movie") == null) {
                ticketPoolService.createTicketPool("movie",
                        configuration.getTotalTickets(),
                        configuration.getMaxTicketCapacity());
            }
            TicketPool ticketPool = ticketPoolService.getTicketPoolByName("movie");
            // Assigning the max ticket capacity to the ticket pool object from the configuration settings
            ticketPool.setMaxTicketCapacity(configuration.getMaxTicketCapacity());

            // If the available ticket count is null, we add ticekts as per in the configuration settings
            if (ticketPool.getAvailableTickets() == 0){
                ticketPool.addTickets(configuration.getTotalTickets());
            }


            // Creating Customers and Vendors
            Customer customer1 = new Customer("Customer A", configuration.getCustomerRetrievalRate(), 5, 0 );
            Customer customer2 = new Customer("Customer B", configuration.getCustomerRetrievalRate(), 10, 0);
            Customer customer3 = new Customer("Customer C", configuration.getCustomerRetrievalRate(), 2, 0);
            Vendor vendor1 = new Vendor("Vendor A", 10, configuration.getTicketReleaseRate());
            Vendor vendor2 = new Vendor("Vendor B", 5, configuration.getTicketReleaseRate());
            Vendor vendor3 = new Vendor("Vendor C", 2, configuration.getTicketReleaseRate());

            // Create tasks using TaskFactory
            CustomerTask customerTask1 = taskFactory.createCustomerTask(customer1, ticketPool);
            CustomerTask customerTask2 = taskFactory.createCustomerTask(customer2, ticketPool);
            CustomerTask customerTask3 = taskFactory.createCustomerTask(customer3, ticketPool);

            VendorTask vendorTask1 = taskFactory.createVendorTask(vendor1, ticketPool);
            VendorTask vendorTask2 = taskFactory.createVendorTask(vendor2, ticketPool);
            VendorTask vendorTask3 = taskFactory.createVendorTask(vendor3, ticketPool);

            // Submit tasks to ExecutorService
            executorService.submit(customerTask1);
            executorService.submit(customerTask2);
            executorService.submit(customerTask3);
            executorService.submit(vendorTask1);
            executorService.submit(vendorTask2);
            executorService.submit(vendorTask3);

            logger.info("All tasks have been started.");

        } else if (running.compareAndSet(true, false)) {
            // LOGIC to stop
            logger.info("Stopping tasks...");
            executorService.shutdownNow(); // Immediately stops all running tasks
        } else {
            logger.error("Tasks are already in the requested state.");
        }

    }

    // Method to save configuration settings in JSON format
    @Override
    public void saveToJson(String filePath, Configuration configuration) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            String configJson = gson.toJson(configuration);
            bw.write(configJson);
            bw.newLine(); // Add a new line after each JSON object
            logger.info("Configuration saved successfully!");

        } catch (Exception e) {
            logger.error("Failed to save configuration: " + e.getMessage());
        }
    }

    // Method to load configuration settings from JSON format
    @Override
    public Configuration loadConfiguration(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            logger.info("Configuration loaded successfully!");
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            logger.error("Error loading configuration: " + e.getMessage());
            return null;
        }
    }

    // Method to get the most recent configuration
    public Configuration getLatestConfig(){

        if (configurationMap.isEmpty()) {
            return null; // Return null if the map is empty
        }

        // Get the last key from the map
        Long lastKey = null;
        for (Long key : configurationMap.keySet()) {
            lastKey = key; // This will store the last key encountered
        }

        // Return the configuration associated with the last key
        return configurationMap.get(lastKey);
        }


}
