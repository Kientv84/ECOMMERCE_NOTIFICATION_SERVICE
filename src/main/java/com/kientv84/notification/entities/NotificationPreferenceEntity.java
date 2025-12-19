package com.kientv84.notification.entities;

import com.kientv84.notification.utils.NotificationChannelType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification_preferences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "channel"}))
public class NotificationPreferenceEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannelType channel;

    @Column(nullable = false)
    private Boolean enabled = true;

    // getter / setter
}

