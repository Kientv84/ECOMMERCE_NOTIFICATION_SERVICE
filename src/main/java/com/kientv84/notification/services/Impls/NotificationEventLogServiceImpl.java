package com.kientv84.notification.services.Impls;

import com.kientv84.notification.entities.NotificationEventLogEntity;
import com.kientv84.notification.repositories.NotificationEventLogRepository;
import com.kientv84.notification.services.NotificationEventLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationEventLogServiceImpl implements NotificationEventLogService {
    private final NotificationEventLogRepository repository;

    @Override
    public boolean isProcessed(String eventId) {
        log.info("checking exist eventType ... ");
        try {
            return repository.existsById(eventId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void markProcessed(String eventId, String eventType) {
        NotificationEventLogEntity log = new NotificationEventLogEntity();
        log.setEventId(eventId);
        log.setEventType(eventType);
        log.setProcessedAt(Instant.now());

        repository.save(log);
    }
}
