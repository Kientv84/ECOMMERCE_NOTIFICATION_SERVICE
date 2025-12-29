package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationTemplateEntity;
import com.kientv84.notification.utils.NotificationChannelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NotificationTemplateRepository  extends JpaRepository<NotificationTemplateEntity, UUID> {
    Optional<NotificationTemplateEntity>
    findFirstByEventTypeAndChannelAndLocaleAndActiveTrueOrderByVersionDesc(
            String eventType,
            NotificationChannelType channel,
            String locale
    );
}
