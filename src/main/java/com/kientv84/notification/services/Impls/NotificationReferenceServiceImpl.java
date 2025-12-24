package com.kientv84.notification.services.Impls;

import com.kientv84.notification.entities.NotificationPreferenceEntity;
import com.kientv84.notification.repositories.NotificationPreferenceRepository;
import com.kientv84.notification.services.NotificationReferenceService;
import com.kientv84.notification.utils.NotificationChannelType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationReferenceServiceImpl
        implements NotificationReferenceService {

    private final NotificationPreferenceRepository repository;

    @Override
    public List<NotificationChannelType> getEnabledChannels(String userId) {
        return repository
                .findByUserIdAndEnabledTrue(userId)
                .stream()
                .map(NotificationPreferenceEntity::getChannel)
                .toList();
    }
}

