package com.example.demo.controller.configurationController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.model.configuration.Configuration;

public interface ConfigurationController {
    CommonResponse createConfiguration (Configuration configuration);
    CommonResponse runTask();
}
