package com.kientv84.notification.dtos.responses.payment;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentNotificationResponse {
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private String orderCode;
    private KafkaPaymentMethodResponse paymentMethod;
    private BigDecimal amount;
    private String status;
    private String transactionCode;
    private Date createdDate;
    private Date updateDate;
}
