package com.kientv84.notification.services.Impls;

import com.kientv84.notification.entities.NotificationTemplateEntity;
import com.kientv84.notification.repositories.NotificationTemplateRepository;
import com.kientv84.notification.services.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTemplateServiceImpl
        implements NotificationTemplateService {

    private final NotificationTemplateRepository repository;

    @Override
    public NotificationTemplateEntity getTemplate(String eventType) {
        return repository
                .findByEventTypeAndActiveTrue(eventType)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No active template for eventType=" + eventType
                        )
                );
    }
}
