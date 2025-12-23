package com.kientv84.notification.dispatcher.handlers.Impls;

import com.kientv84.notification.commons.EventType;
import com.kientv84.notification.dispatcher.handlers.NotificationEventHandler;
import com.kientv84.notification.dtos.kafkaWrappers.KafkaEvent;
import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import com.kientv84.notification.dtos.responses.order.KafkaOrderResponse;
import com.kientv84.notification.mappers.OrderCreatedNotificationEventMapper;
import com.kientv84.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedNotificationHandler
        implements NotificationEventHandler<KafkaOrderResponse> {

    private final NotificationService notificationService;
    private final OrderCreatedNotificationEventMapper mapper;

    @Override
    public String eventType() {
        return EventType.ORDER_CREATED.name();
    }

    @Override
    public NotificationEventDTO<?> handle(
            KafkaEvent<KafkaOrderResponse> event
    ) {
        // 1. Map KafkaEvent -> NotificationEventDTO
        NotificationEventDTO<?> notificationEvent = mapper.map(event);

        // 2. Call service xử lý nghiệp vụ
        notificationService.handleEvent(notificationEvent);

        // 3. Return (optional – cho dispatcher log/audit)
        return notificationEvent;
    }
}

