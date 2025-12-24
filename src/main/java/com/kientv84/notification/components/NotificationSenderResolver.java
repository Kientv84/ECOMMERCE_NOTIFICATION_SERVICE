package com.kientv84.notification.components;

import com.kientv84.notification.services.NotificationSenderService;
import com.kientv84.notification.utils.NotificationChannelType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationSenderResolver {

    private final Map<NotificationChannelType, NotificationSenderService> senderMap;

    public NotificationSenderResolver(
            List<NotificationSenderService> senders
    ) {
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(
                        NotificationSenderService::channel,
                        Function.identity()
                ));
    }

    public NotificationSenderService resolve(NotificationChannelType channel) {
        NotificationSenderService sender = senderMap.get(channel);
        if (sender == null) {
            throw new IllegalArgumentException(
                    "Unsupported channel: " + channel
            );
        }
        return sender;
    }
}

