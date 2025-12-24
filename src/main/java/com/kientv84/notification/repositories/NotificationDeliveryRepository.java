package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationDeliveryEntity;
import com.kientv84.notification.utils.NotificationChannelType;
import com.kientv84.notification.utils.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface NotificationDeliveryRepository
        extends JpaRepository<NotificationDeliveryEntity, UUID> {

    default void saveSuccess(
            UUID notificationId,
            NotificationChannelType channel
    ) {
        NotificationDeliveryEntity entity =
                new NotificationDeliveryEntity();

        entity.setNotificationId(notificationId);
        entity.setChannel(channel);
        entity.setStatus(NotificationStatus.SENT);
        entity.setSentAt(Instant.now());

        save(entity);
    }

    default void saveFailure(
            UUID notificationId,
            NotificationChannelType channel,
            String errorMessage
    ) {
        NotificationDeliveryEntity entity =
                new NotificationDeliveryEntity();

        entity.setNotificationId(notificationId);
        entity.setChannel(channel);
        entity.setStatus(NotificationStatus.FAILED);
        entity.setErrorMessage(errorMessage);
        entity.setSentAt(Instant.now());

        save(entity);
    }
}

