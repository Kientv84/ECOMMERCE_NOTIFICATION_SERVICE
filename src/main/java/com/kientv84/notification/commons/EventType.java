package com.kientv84.notification.commons;

public enum EventType {
    // ===== ORDER =====
    ORDER_CREATED,
    ORDER_READY_FOR_SHIPPING,
    ORDER_PAYMENT_UPDATED,
    ORDER_CANCELLED,

    // ===== PAYMENT =====
    PAYMENT_SUCCESS,
    PAYMENT_FAILED,
    PAYMENT_COD_PENDING,
    PAYMENT_ERROR
}


