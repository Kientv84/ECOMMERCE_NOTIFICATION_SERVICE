package com.kientv84.notification.dispatcher;
import com.kientv84.notification.dtos.kafkaWrappers.KafkaEvent;
import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import com.kientv84.notification.dispatcher.handlers.NotificationEventHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationEventDispatcher {

    private final Map<String, NotificationEventHandler<?>> handlerMap;

    public NotificationEventDispatcher(
            List<NotificationEventHandler<?>> handlers
    ) {
        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(
                        NotificationEventHandler::eventType,
                        Function.identity()
                ));
    }

    @SuppressWarnings("unchecked")
    public NotificationEventDTO<?> dispatch(KafkaEvent<?> event) {
        String eventType = event.getMetadata().getEventType();

        NotificationEventHandler handler = handlerMap.get(eventType);

        if (handler == null) {
            throw new IllegalArgumentException(
                    "Unsupported event type: " + eventType
            );
        }

        return handler.handle(event);
    }
}