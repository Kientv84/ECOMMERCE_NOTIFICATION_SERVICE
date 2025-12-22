package com.kientv84.notification.dtos.responses.order;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderNotificationResponse {

    private UUID userId;
    private String orderCode;
    private BigDecimal totalPrice;
    private String shippingAddress;
}

