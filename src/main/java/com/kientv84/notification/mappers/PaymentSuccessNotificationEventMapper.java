package com.kientv84.notification.mappers;

import com.kientv84.notification.dtos.kafkaWrappers.KafkaEvent;
import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import com.kientv84.notification.dtos.responses.payment.KafkaPaymentResponse;
import com.kientv84.notification.dtos.responses.payment.PaymentNotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PaymentSuccessNotificationEventMapper {
    // ===== map metadata =====
    @Mapping(target = "eventId", source = "metadata.eventId")
    @Mapping(target = "eventType", source = "metadata.eventType")
    @Mapping(target = "source", source = "metadata.source")
    @Mapping(target = "version", source = "metadata.version")
    // ===== map payload (NESTED) =====
    @Mapping(target = "payload.id", source = "payload.id")
    @Mapping(target = "payload.orderId", source = "payload.orderId")
    @Mapping(target = "payload.userId", source = "payload.userId")
    @Mapping(target = "payload.orderCode", source = "payload.orderCode")
    @Mapping(target = "payload.paymentMethod", source = "payload.paymentMethod")
    @Mapping(target = "payload.amount", source = "payload.amount")
    @Mapping(target = "payload.status", source = "payload.status")
    @Mapping(target = "payload.transactionCode", source = "payload.transactionCode")
    @Mapping(target = "payload.createdDate", source = "payload.createdDate")
    @Mapping(target = "payload.updateDate", source = "payload.updateDate")
    NotificationEventDTO<PaymentNotificationResponse> map(
            KafkaEvent<KafkaPaymentResponse> event
    );
}
