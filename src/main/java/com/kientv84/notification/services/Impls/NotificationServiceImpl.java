package com.kientv84.notification.services.Impls;

import com.kientv84.notification.components.NotificationSenderResolver;
import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import com.kientv84.notification.dtos.responses.NotificationSendResponse;
import com.kientv84.notification.dtos.responses.order.OrderNotificationResponse;
import com.kientv84.notification.entities.NotificationEntity;
import com.kientv84.notification.entities.NotificationTemplateEntity;
import com.kientv84.notification.repositories.NotificationDeliveryRepository;
import com.kientv84.notification.repositories.NotificationRepository;
import com.kientv84.notification.services.*;
import com.kientv84.notification.utils.NotificationChannelType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationEventLogService eventLogService;
    private final NotificationTemplateService templateService;
    private final NotificationReferenceService referenceService;
    private final NotificationSenderResolver senderResolver;

    private final NotificationRepository notificationRepository;
    private final NotificationDeliveryRepository deliveryRepository;

    @Override
    public void handleEvent(NotificationEventDTO<?> event) {
        Map<String, Object> payload = (Map<String, Object>) event.getPayload();
        String userId = (String) payload.get("userId");

        String eventId = event.getEventId();
        String eventType = event.getEventType();

        // 1. Idempotency
        if (eventLogService.isProcessed(eventId)) {
            log.info("[Notification] Event {} already processed", eventId);
            return;
        }

        // 2. Load template
        NotificationTemplateEntity template =
                templateService.getTemplate(eventType);

        // 3. Render content (đơn giản, sau có thể dùng engine)
        String title = template.getTitleTemplate();
        String content = template.getContentTemplate();

        // 4. Persist NotificationEntity
        NotificationEntity notification = NotificationEntity.builder()
                .eventId(eventId)
                .eventType(eventType)
                .userId(userId)
                .title(title)
                .content(content)
                .build();

        notification = notificationRepository.save(notification);

        // 5. Resolve enabled channels
        List<NotificationChannelType> channels =
                referenceService.getEnabledChannels(
                        userId
                );

        // 6. Fan-out per channel
        for (NotificationChannelType channel : channels) {
            try {
                NotificationSendResponse context =
                        NotificationSendResponse.builder()
                                .notificationId(notification.getId())
                                .eventId(eventId)
                                .eventType(eventType)
                                .userId(notification.getUserId())
                                .channel(channel)
                                .title(title)
                                .content(content)
                                .build();

                NotificationSenderService sender =
                        senderResolver.resolve(channel);

                sender.send(context);

                deliveryRepository.saveSuccess(
                        notification.getId(),
                        channel
                );

            } catch (Exception ex) {
                log.error(
                        "[Notification] Failed to send event={} channel={}",
                        eventId, channel, ex
                );

                deliveryRepository.saveFailure(
                        notification.getId(),
                        channel,
                        ex.getMessage()
                );
            }
        }

        // 7. Mark processed
        eventLogService.markProcessed(eventId, eventType);
    }
}