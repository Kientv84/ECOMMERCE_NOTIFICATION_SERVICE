package com.kientv84.notification.services;

public interface NotificationEventLogService {

    boolean isProcessed(String eventId);

    void markProcessed(String eventId, String eventType);
}

