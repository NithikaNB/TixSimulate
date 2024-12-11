package com.example.demo.service.logService;

import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class LogServiceImpl implements LogService{

    // ATTRIBUTES //
    private final SimpMessagingTemplate messagingTemplate;

    // CONSTRUCTOR //
    @Autowired
    public LogServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    // METHODS //

    @Override
    public void sendLog(String log) {
        messagingTemplate.convertAndSend("/topic/logs", log); // Send message to subscribers

    }

}
