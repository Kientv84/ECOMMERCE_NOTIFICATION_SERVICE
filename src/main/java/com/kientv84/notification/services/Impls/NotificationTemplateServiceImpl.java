package com.kientv84.notification.services.Impls;

import com.kientv84.notification.entities.NotificationTemplateEntity;
import com.kientv84.notification.repositories.NotificationTemplateRepository;
import com.kientv84.notification.services.NotificationTemplateService;
import com.kientv84.notification.utils.NotificationChannelType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTemplateServiceImpl
        implements NotificationTemplateService {

    private static final String DEFAULT_LOCALE = "vi";

    private final NotificationTemplateRepository repository;

    @Override
    public NotificationTemplateEntity getTemplate(
            String eventType,
            NotificationChannelType channel,
            String locale
    ) {
        // 1. Try user locale
        return repository
                .findFirstByEventTypeAndChannelAndLocaleAndActiveTrueOrderByVersionDesc(
                        eventType, channel, locale
                )
                // 2. Fallback default locale
                .or(() -> repository
                        .findFirstByEventTypeAndChannelAndLocaleAndActiveTrueOrderByVersionDesc(
                                eventType, channel, DEFAULT_LOCALE
                        ))
                // 3. No template -> fail fast
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No template found for eventType=" + eventType
                                        + ", channel=" + channel
                                        + ", locale=" + locale
                        )
                );
    }
}
