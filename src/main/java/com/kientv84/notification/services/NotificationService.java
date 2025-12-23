package com.kientv84.notification.services;

import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import org.springframework.stereotype.Service;

public interface NotificationService {
    void handleEvent(NotificationEventDTO<?> event);
}
