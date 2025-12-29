package com.kientv84.notification.services.Impls;

import com.kientv84.notification.components.NotificationSenderResolver;
import com.kientv84.notification.dtos.responses.NotificationEventDTO;
import com.kientv84.notification.dtos.responses.NotificationSendResponse;
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
        String eventId = event.getEventId();
        String eventType = event.getEventType();
        String userId = event.getUserId();
        String locale = event.getLocale() != null ? event.getLocale() : "vi";

        // 1. Idempotency
        if (eventLogService.isProcessed(eventId)) {
            log.info("[Notification] Event {} already processed", eventId);
            return;
        }

        // 2. Persist base notification (event-level)
        NotificationEntity notification = notificationRepository.save(
                NotificationEntity.builder()
                        .eventId(eventId)
                        .eventType(eventType)
                        .userId(userId)
                        .build()
        );

        // 3. Resolve enabled channels
        List<NotificationChannelType> channels =
                referenceService.getEnabledChannels(userId);

        // 4. Fan-out per channel
        for (NotificationChannelType channel : channels) {
            try {
                // 4.1 Load template per channel
                NotificationTemplateEntity template =
                        templateService.getTemplate(
                                eventType,
                                channel,
                                locale
                        );

                // 4.2 Render (sau có thể dùng engine)
                String title = template.getTitleTemplate();
                String content = template.getContentTemplate();

                // 4.3 Send
                NotificationSenderService sender =
                        senderResolver.resolve(channel);

                sender.send(
                        NotificationSendResponse.builder()
                                .notificationId(notification.getId())
                                .eventId(eventId)
                                .eventType(eventType)
                                .userId(userId)
                                .channel(channel)
                                .title(title)
                                .content(content)
                                .build()
                );

                // 4.4 Persist success
                deliveryRepository.markSuccess(
                        notification.getId(),
                        channel
                );

            } catch (Exception ex) {
                log.error(
                        "[Notification] Failed event={} channel={}",
                        eventId, channel, ex
                );

                // Persist failure
                deliveryRepository.markFailure(
                        notification.getId(),
                        channel,
                        ex.getMessage()
                );
            }
        }

        // 5. Mark processed
        eventLogService.markProcessed(eventId, eventType);
    }
}

//Khi một service (ví dụ Order Service) produce một event lên Kafka để trigger notification, event đó bắt buộc phải chứa eventId, eventType và userId trong metadata.
//Notification Service subscribe các eventType mà nó quan tâm. Khi nhận được message, hệ thống sẽ mapping event bên ngoài sang NotificationEventDTO nội bộ.
//Tại tầng xử lý chính, Notification Service sẽ kiểm tra idempotency thông qua eventId. Nếu event đã được xử lý trước đó thì bỏ qua.
//Nếu event chưa từng xử lý, service sẽ load template tương ứng với eventType, render nội dung thông báo, lưu notification vào database.
//Sau đó, hệ thống truy vấn user preference để xác định các kênh được bật (email, push, sms…), fan-out gửi notification theo từng kênh và lưu trạng thái delivery cho từng channel.
//Cuối cùng, event được đánh dấu là đã xử lý để đảm bảo không bị xử lý lại trong trường hợp consumer retry.