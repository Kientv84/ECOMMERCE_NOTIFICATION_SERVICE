package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationEventLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationEventLogRepository extends JpaRepository<NotificationEventLogEntity, String> {
    boolean existsByEventId(String eventId);
}
