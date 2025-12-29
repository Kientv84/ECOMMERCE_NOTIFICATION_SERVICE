package com.kientv84.notification.dtos.responses;

import lombok.*;

import java.time.Instant;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEventDTO<T> {
    // ===== Metadata dùng cho tracing / idempotency =====
    private String eventId;
    private String eventType;
    private String source;
    private int version;
    private String locale;


    private String userId;
    // ===== Data cho template =====
    private T payload;
}
