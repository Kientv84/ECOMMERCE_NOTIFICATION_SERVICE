package com.kientv84.notification.services;

import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService{
    @Override
    public void handleEvent(NotificationEventDTO<?> event) {
        log.info(
                "[NotificationService] Handle eventType={}, payload={}",
                event.getEventType(),
                event.getPayload()
        );

        // TODO:
        // - render template
        // - send email / push
        // - save DB
    }
}
