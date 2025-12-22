package com.kientv84.notification.dtos.kafkaWrappers;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaEvent<T> {
    private EventMetadata metadata;
    private T payload;
}

