package com.kientv84.notification.mappers;

import com.kientv84.notification.dtos.kafkaWrappers.KafkaEvent;
import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import com.kientv84.notification.dtos.responses.order.KafkaOrderResponse;
import com.kientv84.notification.dtos.responses.order.OrderNotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCreatedNotificationEventMapper {
    // ===== map metadata =====
    @Mapping(target = "eventId", source = "metadata.eventId")
    @Mapping(target = "eventType", source = "metadata.eventType")
    @Mapping(target = "source", source = "metadata.source")
    @Mapping(target = "version", source = "metadata.version")
    // ===== map payload (NESTED) =====
    @Mapping(target = "payload.userId", source = "payload.userId")
    @Mapping(target = "payload.orderCode", source = "payload.orderCode")
    @Mapping(target = "payload.totalPrice", source = "payload.totalPrice")
    @Mapping(target = "payload.shippingAddress", source = "payload.shippingAddress")
    NotificationEventDTO<OrderNotificationResponse> map(
            KafkaEvent<KafkaOrderResponse> event
    );
}


