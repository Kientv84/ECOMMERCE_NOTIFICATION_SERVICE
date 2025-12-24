package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NotificationTemplateRepository  extends JpaRepository<NotificationTemplateEntity, UUID> {
    Optional<NotificationTemplateEntity>
    findByEventTypeAndActiveTrue(String eventType);
}
