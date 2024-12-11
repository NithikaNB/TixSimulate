package com.example.demo.service.logService;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAppender extends AppenderBase<ILoggingEvent> {
    private static LogService logService;


    @Autowired
    public void setLogService(LogService logService) {
        WebSocketAppender.logService = logService;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (logService != null) {
            String logMessage = eventObject.getFormattedMessage();
            logService.sendLog(logMessage); // Broadcast log via WebSocket
        }
    }



}
