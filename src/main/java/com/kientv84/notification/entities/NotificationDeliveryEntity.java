package com.kientv84.notification.entities;

import com.kientv84.notification.utils.NotificationChannelType;
import com.kientv84.notification.utils.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "notification_deliveries")
public class NotificationDeliveryEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID notificationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannelType channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(nullable = false)
    private Instant sentAt = Instant.now();

}

