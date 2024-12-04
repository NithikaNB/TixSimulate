package com.example.demo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import lombok.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

//@AllArgsConstructor
@RequiredArgsConstructor
@Getter
//@Setter
//@NoArgsConstructor

public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;
    //Instantiate Gson Object
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    //Attributes (RequiredArgsConstructor will only be used for these)
    @NonNull
    @Expose
    private int totalTickets;

    @NonNull
    @Expose
    private int ticketReleaseRate;

    @NonNull
    @Expose
    private int customerRetrievalRate;

    @NonNull
    @Expose
    private int maxTicketCapacity;



    public void setTotalTickets(int totalTickets) {
        if (totalTickets < 0) {
            throw new IllegalArgumentException("Total tickets cannot be negative.");
        }
        if (totalTickets > maxTicketCapacity) {
            throw new IllegalArgumentException("Total tickets cannot exceed max ticket capacity.");
        }
        this.totalTickets = totalTickets;
    }


    public void setTicketReleaseRate(int ticketReleaseRate) {
        if (ticketReleaseRate <= 0) {
            throw new IllegalArgumentException("Ticket release rate must be greater than zero.");
        }
        this.ticketReleaseRate = ticketReleaseRate;
    }


    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        if (customerRetrievalRate <= 0) {
            throw new IllegalArgumentException("Customer retrieval rate must be greater than zero.");
        }
        this.customerRetrievalRate = customerRetrievalRate;
    }


    public void setMaxTicketCapacity(int maxTicketCapacity) {
        if (maxTicketCapacity <= 0){
            throw new IllegalArgumentException ("Max ticket capacity must be greater than zero.");
        }
        if (totalTickets > maxTicketCapacity){
            throw new IllegalArgumentException("Total tickets cannot exceed max ticket capacity.");
        }
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void saveToJson(String filePath) throws Exception {
        try (FileWriter fw = new FileWriter(filePath);) {
            JsonElement configJson = gson.toJsonTree(this);
//            System.out.println(configJson);
//            System.out.println(this);
            gson.toJson(configJson, fw);

        } catch (Exception e) {
            System.err.println("Failed to save configuration: " + e.getMessage());


        }
    }

    public Configuration loadConfiguration(String filePath){
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            return null;
        }
    }

}
