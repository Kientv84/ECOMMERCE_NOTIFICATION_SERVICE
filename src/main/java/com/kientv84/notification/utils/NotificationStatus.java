package com.kientv84.notification.utils;

public enum NotificationStatus {
    CREATED,              // Notification đã được tạo
    DELIVERED,            // Tất cả channel thành công
    PARTIAL_DELIVERED,    // Một số channel thành công
    FAILED                // Tất cả channel thất bại
}

