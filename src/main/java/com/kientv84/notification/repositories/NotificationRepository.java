package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

}
