package com.kientv84.notification.dtos.kafkaWrappers;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventMetadata {
    private UUID eventId;
    private String eventType;
    private String source;
    private int version;
}


