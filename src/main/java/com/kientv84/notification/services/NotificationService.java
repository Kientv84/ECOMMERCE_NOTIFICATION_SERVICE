package com.kientv84.notification.services;

import com.kientv84.notification.dtos.responses.NotificationEventDTO;

public interface NotificationService {
    void handleEvent(NotificationEventDTO event);
}
