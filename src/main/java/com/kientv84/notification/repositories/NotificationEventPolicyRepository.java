package com.kientv84.notification.repositories;

import com.kientv84.notification.entities.NotificationEventPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationEventPolicyRepository extends JpaRepository<NotificationEventPolicyEntity, UUID> {
}
