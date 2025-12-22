package com.kientv84.notification.dtos.responses;

import java.time.Instant;
import java.util.Map;

public class NotificationEventDTO<T> {
    // ===== Metadata dùng cho tracing / idempotency =====
    private String eventId;
    private String eventType;
    private String source;
    private int version;
    // ===== Data cho template =====
    private T payload;
}
