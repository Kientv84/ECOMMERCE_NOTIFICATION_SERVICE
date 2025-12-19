package com.kientv84.notification.utils;

public enum NotificationStatus {
    PENDING,   // Đã tạo, chưa gửi
    SENT,      // Gửi thành công
    FAILED     // Gửi thất bại (có thể retry)
}

