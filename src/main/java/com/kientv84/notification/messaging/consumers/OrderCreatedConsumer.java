package com.kientv84.notification.messaging.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kientv84.notification.commons.EventType;
import com.kientv84.notification.dispatcher.NotificationEventDispatcher;
import com.kientv84.notification.dtos.kafkaWrappers.KafkaEvent;
import com.kientv84.notification.dtos.responses.order.KafkaOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderCreatedConsumer {
    private final ObjectMapper objectMapper;
    private final NotificationEventDispatcher dispatcher;

    @KafkaListener(
            topics = "${spring.kafka.order.topic.order-created}",
            groupId = "${spring.kafka.order.group}"
    )
    public void onMessage(@Payload String message) {
        try {
            // 1. Parse raw event (payload vẫn là Map)
            KafkaEvent<?> rawEvent =
                    objectMapper.readValue(
                            message,
                            new TypeReference<KafkaEvent<?>>() {}
                    );

            String eventType = rawEvent.getMetadata().getEventType();

            KafkaEvent<?> finalEvent;

            // 2. Resolve payload theo eventType
            if (EventType.ORDER_CREATED.name().equals(eventType)) {

                KafkaOrderResponse payload =
                        objectMapper.convertValue(
                                rawEvent.getPayload(),
                                KafkaOrderResponse.class
                        );

                finalEvent = KafkaEvent.builder()
                        .metadata(rawEvent.getMetadata())
                        .payload(payload)
                        .build();

            } else {
                throw new IllegalArgumentException(
                        "Unsupported eventType: " + eventType
                );
            }

            // 3. Dispatch
            dispatcher.dispatch(finalEvent);

        } catch (Exception e) {
            log.error("[NotificationConsumer] Error while processing message", e);
        }
    }
}
