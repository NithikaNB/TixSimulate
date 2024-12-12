package com.example.demo.service.configurationService;

import com.example.demo.model.configuration.Configuration;

import java.util.logging.Logger;

public interface ConfigurationService {
    void createConfig(Configuration configuration);
    void runTask();
    void saveToJson(String filePath, Configuration configuration);
    Configuration loadConfiguration(String filePath);
}
