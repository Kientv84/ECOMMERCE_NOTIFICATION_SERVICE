package com.kientv84.notification.services;

import com.kientv84.notification.utils.NotificationChannelType;

import java.util.List;

public interface NotificationReferenceService {

    List<NotificationChannelType> getEnabledChannels(String userId);
}

