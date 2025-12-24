package com.kientv84.notification.services;

import com.kientv84.notification.entities.NotificationTemplateEntity;

public interface NotificationTemplateService {
    NotificationTemplateEntity getTemplate(String eventType);
}
