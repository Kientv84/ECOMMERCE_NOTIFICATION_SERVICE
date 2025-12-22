package com.kientv84.notification.dispatcher.handlers.Impls;

import com.kientv84.notification.commons.EventType;
import com.kientv84.notification.dispatcher.handlers.NotificationEventHandler;
import com.kientv84.notification.dtos.kafkaWrappers.KafkaEvent;
import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import com.kientv84.notification.dtos.responses.order.KafkaOrderResponse;
import com.kientv84.notification.mappers.OrderCreatedNotificationEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedNotificationHandler
        implements NotificationEventHandler<KafkaOrderResponse> {

    private final OrderCreatedNotificationEventMapper mapper;

    @Override
    public String eventType() {
        return EventType.ORDER_CREATED.name();
    }

    @Override
    public NotificationEventDTO<?> handle(
            KafkaEvent<KafkaOrderResponse> event
    ) {
        return mapper.map(event);
    }
}

