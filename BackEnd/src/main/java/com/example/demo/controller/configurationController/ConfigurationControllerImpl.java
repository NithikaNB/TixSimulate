package com.example.demo.controller.configurationController;

import com.example.demo.dto.response.CommonResponse;
import com.example.demo.dto.response.ResponseConstants;
import com.example.demo.model.configuration.Configuration;
import com.example.demo.service.configurationService.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuration")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationControllerImpl implements ConfigurationController{

    // ATTRIBUTES //
    private final ConfigurationService configurationService;

    // CONSTRUCTOR //
    @Autowired
    public ConfigurationControllerImpl(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }


    // METHODS //

    @PostMapping("/create-config")
    @Override
    public CommonResponse createConfiguration(@RequestBody Configuration configuration) {
        try {
            configurationService.createConfig(configuration);
            return new CommonResponse(ResponseConstants.SUCCESS,"Configuration has been successfully created.");
        }catch (Exception e){
            String message = "Error while processing runTask: " + e.getMessage();
            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, message);

        }
    }

    @PostMapping("/run-task")
    @Override
    public CommonResponse runTask() {
        try {
            configurationService.runTask();
            return new CommonResponse(ResponseConstants.SUCCESS, "Task has been successfully started/stopped.");
        }catch (Exception e){
            String message = "Error while processing runTask: " + e.getMessage();
            return new CommonResponse(ResponseConstants.UNSUCCESSFUL, message);
        }
    }

    @Override
    public CommonResponse getLogger() {
        return null;
    }
}
