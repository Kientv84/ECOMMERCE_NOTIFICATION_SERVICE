package com.kientv84.notification.entities;

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
@Table(name = "notification_templates",
        uniqueConstraints = @UniqueConstraint(columnNames = "eventType")) //Mỗi eventType chỉ được tồn tại 1 template
public class NotificationTemplateEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false, length = 5)
    private String locale;

    @Column(nullable = false)
    private String titleTemplate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contentTemplate;

    @Column(nullable = false)
    private Boolean active = true;

    // getter / setter
}
