package com.kientv84.notification.dispatcher.handlers.Impls;

import com.kientv84.notification.commons.EventType;
import com.kientv84.notification.dispatcher.handlers.NotificationEventHandler;
import com.kientv84.notification.dtos.kafkaWrappers.KafkaEvent;
import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import com.kientv84.notification.dtos.responses.payment.KafkaPaymentResponse;
import com.kientv84.notification.mappers.OrderCreatedNotificationEventMapper;
import com.kientv84.notification.mappers.PaymentSuccessNotificationEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentSuccessNotificationHandler implements NotificationEventHandler<KafkaPaymentResponse> {
    private final PaymentSuccessNotificationEventMapper mapper;

    @Override
    public String eventType() {
        return EventType.PAYMENT_SUCCESS.name();
    }

    @Override
    public NotificationEventDTO<?> handle(KafkaEvent<KafkaPaymentResponse> event) {
        return mapper.map(event);
    }
}
