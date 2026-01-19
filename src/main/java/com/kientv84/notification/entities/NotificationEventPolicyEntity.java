package com.kientv84.notification.entities;

import com.kientv84.notification.commons.NotificationEventType;
import com.kientv84.notification.utils.NotificationChannelType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "notification_event_policy",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"event_type", "channel"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEventPolicyEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 50)
    private NotificationEventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationChannelType channel;

    @Column(nullable = false)
    private boolean mandatory;

    @Column
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}