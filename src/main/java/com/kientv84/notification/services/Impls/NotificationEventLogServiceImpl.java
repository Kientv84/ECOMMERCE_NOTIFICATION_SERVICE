package com.kientv84.notification.services.Impls;

import com.kientv84.notification.entities.NotificationEventLogEntity;
import com.kientv84.notification.repositories.NotificationEventLogRepository;
import com.kientv84.notification.services.NotificationEventLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationEventLogServiceImpl implements NotificationEventLogService {
    private final NotificationEventLogRepository repository;

    @Override
    public boolean isProcessed(String eventId) {
        return repository.existsByEventId(eventId);
    }

    @Override
    public void markProcessed(String eventId, String eventType) {
        try {
            repository.save(
                    NotificationEventLogEntity.builder()
                            .eventId(eventId)
                            .eventType(eventType)
                            .processedAt(Instant.now())
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            // event đã được insert bởi consumer khác
            log.warn("Event {} already marked processed", eventId);
        }
    }
}
