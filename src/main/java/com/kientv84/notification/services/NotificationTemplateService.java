package com.kientv84.notification.services;

import com.kientv84.notification.entities.NotificationTemplateEntity;
import com.kientv84.notification.utils.NotificationChannelType;

public interface NotificationTemplateService {
    NotificationTemplateEntity getTemplate( String eventType,
                                            NotificationChannelType channel,
                                            String locale);
}
