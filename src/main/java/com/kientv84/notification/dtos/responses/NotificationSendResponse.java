package com.kientv84.notification.dtos.responses;

import com.kientv84.notification.utils.NotificationChannelType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class NotificationSendResponse {
    private UUID notificationId;
    private String eventId;
    private String eventType;
    private String userId;
    private NotificationChannelType channel;
    private String title;
    private String content;
    // optional
    private String locale;
    // channel-specific metadata
    private Map<String, Object> metadata;
}
