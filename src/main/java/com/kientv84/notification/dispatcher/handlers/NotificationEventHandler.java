package com.kientv84.notification.dispatcher.handlers;

import com.kientv84.notification.dtos.kafkaWrappers.KafkaEvent;
import com.kientv84.notification.dtos.responses.NotificationEventDTO;

public interface NotificationEventHandler<T> {

    /**
     * Trả về eventType mà handler này xử lý
     * VD: ORDER_CREATED, PAYMENT_SUCCESS
     */
    String eventType();

    /**
     * Xử lý event và trả về NotificationEventDTO
     */
    NotificationEventDTO<?> handle(KafkaEvent<T> event);
}
