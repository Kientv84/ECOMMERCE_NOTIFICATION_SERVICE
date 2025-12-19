package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationPreferenceEntity;
import com.kientv84.notification.utils.NotificationChannelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationPreferenceRepository  extends JpaRepository<NotificationPreferenceEntity, UUID> {
    List<NotificationPreferenceEntity> findByUserIdAndEnabledTrue(String userId);

    boolean existsByUserIdAndChannelAndEnabledTrue(
            String userId,
            NotificationChannelType channel
    );
}
