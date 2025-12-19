package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationTemplateRepository  extends JpaRepository<NotificationTemplateEntity, UUID> {
    NotificationTemplateEntity findByEventTypeAndLocaleAndActiveTrue(String eventType, String locale);
}
