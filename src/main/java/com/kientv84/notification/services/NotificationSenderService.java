package com.kientv84.notification.services;

import com.kientv84.notification.dtos.responses.NotificationSendResponse;
import com.kientv84.notification.utils.NotificationChannelType;

public interface NotificationSenderService {

    NotificationChannelType channel();

    void send(NotificationSendResponse context);
}

