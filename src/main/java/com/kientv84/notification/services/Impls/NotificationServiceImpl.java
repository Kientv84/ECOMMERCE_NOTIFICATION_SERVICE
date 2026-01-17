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
import com.kientv84.notification.utils.NotificationStatus;
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public void handleEvent(NotificationEventDTO<?> event) {
        String eventId = event.getEventId();
        String eventType = event.getEventType();
        String userId = event.getUserId();
        String locale = event.getLocale() != null ? event.getLocale() : "vi";

        log.info(
                "[Notification] Start processing eventId={} eventType={} userId={} locale={}",
                eventId, eventType, userId, locale
        );

        // 1. Idempotency (event-level)
        if (eventLogService.isProcessed(eventId)) {
            log.warn(
                    "[Notification] Event {} already processed, skip",
                    eventId
            );
            return;
        }

        // 2. Load enabled channels
        List<NotificationChannelType> enabledChannels =
                referenceService.getEnabledChannels(userId);

        log.info(
                "[Notification] Enabled channels for user {}: {}",
                userId, enabledChannels
        );

        if (enabledChannels.isEmpty()) {
            log.warn(
                    "[Notification] User {} has no enabled channels, mark event {} as processed",
                    userId, eventId
            );
            eventLogService.markProcessed(eventId, eventType);
            return;
        }

        // 3. Process per channel
        for (NotificationChannelType channel : enabledChannels) {

            log.info(
                    "[Notification] Processing channel={} for eventId={}",
                    channel, eventId
            );

            try {
                // 3.1 Load template
                NotificationTemplateEntity template =
                        templateService.getTemplate(eventType, channel, locale);

                log.debug(
                        "[Notification] Loaded template: eventType={} channel={} locale={} version={}",
                        eventType, channel, locale, template.getVersion()
                );

                // 3.2 Persist notification
                NotificationEntity notification =
                        notificationRepository.save(
                                NotificationEntity.builder()
                                        .eventId(eventId)
                                        .eventType(eventType)
                                        .userId(userId)
                                        .title(template.getTitleTemplate())
                                        .content(template.getContentTemplate())
                                        .channel(channel)
                                        .status(NotificationStatus.DELIVERED)
                                        .locale(locale)
                                        .read(false)
                                        .build()
                        );

                log.info(
                        "[Notification] Notification saved: notificationId={} channel={}",
                        notification.getId(), channel
                );

                // 3.3 Mark delivery success
                deliveryRepository.markSuccess(
                        notification.getId(),
                        channel
                );

                log.info(
                        "[Notification] Delivery marked SUCCESS: notificationId={} channel={}",
                        notification.getId(), channel
                );

            } catch (Exception ex) {
                log.error(
                        "[Notification] Failed processing eventId={} channel={} error={}",
                        eventId, channel, ex.getMessage(), ex
                );
            }
        }

        // 4. Mark event processed
        eventLogService.markProcessed(eventId, eventType);

        log.info(
                "[Notification] Finished processing eventId={} eventType={}",
                eventId, eventType
        );
    }
}

//Khi một service (ví dụ Order Service) produce một event lên Kafka để trigger notification, event đó bắt buộc phải chứa eventId, eventType và userId trong metadata.
//Notification Service subscribe các eventType mà nó quan tâm. Khi nhận được message, hệ thống sẽ mapping event bên ngoài sang NotificationEventDTO nội bộ.
//Tại tầng xử lý chính, Notification Service sẽ kiểm tra idempotency thông qua eventId. Nếu event đã được xử lý trước đó thì bỏ qua.
//Nếu event chưa từng xử lý, service sẽ load template tương ứng với eventType, render nội dung thông báo, lưu notification vào database.
//Sau đó, hệ thống truy vấn user preference để xác định các kênh được bật (email, push, sms…), fan-out gửi notification theo từng kênh và lưu trạng thái delivery cho từng channel.
//Cuối cùng, event được đánh dấu là đã xử lý để đảm bảo không bị xử lý lại trong trường hợp consumer retry.

