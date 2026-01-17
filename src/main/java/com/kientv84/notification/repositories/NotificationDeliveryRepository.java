package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationDeliveryEntity;
import com.kientv84.notification.utils.NotificationChannelType;
import com.kientv84.notification.utils.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface NotificationDeliveryRepository
        extends JpaRepository<NotificationDeliveryEntity, UUID> {

    Optional<NotificationDeliveryEntity>
    findByNotificationIdAndChannel(
            UUID notificationId,
            NotificationChannelType channel
    );

    default void markSuccess(
            UUID notificationId,
            NotificationChannelType channel
    ) {
        NotificationDeliveryEntity entity =
                findByNotificationIdAndChannel(notificationId, channel)
                        .orElseGet(() -> {
                            NotificationDeliveryEntity e =
                                    new NotificationDeliveryEntity();
                            e.setNotificationId(notificationId);
                            e.setChannel(channel);
                            e.setSentAt(Instant.now());
                            return e;
                        });

        entity.setStatus(NotificationStatus.DELIVERED);
        entity.setSentAt(Instant.now());
        entity.setErrorMessage(null);

        save(entity);
    }

    default void markFailure(
            UUID notificationId,
            NotificationChannelType channel,
            String errorMessage
    ) {
        NotificationDeliveryEntity entity =
                findByNotificationIdAndChannel(notificationId, channel)
                        .orElseGet(() -> {
                            NotificationDeliveryEntity e =
                                    new NotificationDeliveryEntity();
                            e.setNotificationId(notificationId);
                            e.setChannel(channel);
                            e.setSentAt(Instant.now());
                            return e;
                        });

        entity.setStatus(NotificationStatus.FAILED);
        entity.setErrorMessage(errorMessage);
        entity.setFailedAt(Instant.now());

        save(entity);
    }
}
