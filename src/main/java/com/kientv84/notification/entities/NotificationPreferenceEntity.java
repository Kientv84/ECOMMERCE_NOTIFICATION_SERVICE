package com.kientv84.notification.entities;

import com.kientv84.notification.utils.NotificationChannelType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;@Entity
@Table(
        name = "notification_preferences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "channel"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPreferenceEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationChannelType channel;

    @Column(nullable = false)
    private Boolean enabled = true;
}
