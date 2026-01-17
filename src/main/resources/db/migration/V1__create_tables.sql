
--------------------------------------------------
-- 1. notifications
--------------------------------------------------
CREATE TABLE notifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id VARCHAR(255) NOT NULL,
    event_id VARCHAR(255) NOT NULL UNIQUE,
    event_type VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    read BOOLEAN NOT NULL DEFAULT FALSE,
    channel VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    locale VARCHAR(5) NOT NULL
);

CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_event_type ON notifications(event_type);

--------------------------------------------------
-- 2. notification_deliveries
--------------------------------------------------
CREATE TABLE notification_deliveries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    notification_id UUID NOT NULL,
    channel VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    error_message TEXT,
    sent_at TIMESTAMP WITH TIME ZONE,
    failed_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),

    CONSTRAINT fk_delivery_notification
        FOREIGN KEY (notification_id)
        REFERENCES notifications(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_delivery_notification_id
    ON notification_deliveries(notification_id);

--------------------------------------------------
-- 3. notification_event_logs (idempotency)
--------------------------------------------------
CREATE TABLE notification_event_logs (
    event_id VARCHAR(255) PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

--------------------------------------------------
-- 4. notification_preferences
--------------------------------------------------
CREATE TABLE notification_preferences (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id VARCHAR(255) NOT NULL,
    channel VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT uq_user_channel UNIQUE (user_id, channel)
);

CREATE INDEX idx_preferences_user_id
    ON notification_preferences(user_id);

--------------------------------------------------
-- 5. notification_templates
--------------------------------------------------
CREATE TABLE notification_templates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_type VARCHAR(255) NOT NULL,
    channel VARCHAR(20) NOT NULL,
    locale VARCHAR(5) NOT NULL,
    version INT NOT NULL,
    active BOOLEAN NOT NULL,
    title_template VARCHAR(255) NOT NULL,
    content_template TEXT NOT NULL,

    CONSTRAINT uq_template_version
        UNIQUE (event_type, channel, locale, version)
);

CREATE INDEX idx_templates_lookup
    ON notification_templates(event_type, channel, locale, active);

--------------------------------------------------
-- 6. Seed notification_preferences for existing users
--------------------------------------------------
INSERT INTO notification_preferences (user_id, channel, enabled)
SELECT
    u.user_id,
    c.channel,
    true
FROM (
    VALUES
        ('USER001'),
        ('USER002'),
        ('USER003'),
        ('USER004'),
        ('USER005'),
        ('USER006'),
        ('USER007'),
        ('USER008'),
        ('USER009'),
        ('USER010'),
        ('USER011')
) AS u(user_id)
CROSS JOIN (
    VALUES
        ('IN_APP'),
        ('EMAIL')
) AS c(channel)
ON CONFLICT (user_id, channel) DO NOTHING;
