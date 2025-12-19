package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationDeliveryRepository
        extends JpaRepository<NotificationDeliveryEntity, UUID> {

    List<NotificationDeliveryEntity> findByNotificationId(UUID notificationId);
}

