--------------------------------------------------
-- ORDER_CREATED - IN_APP
--------------------------------------------------
INSERT INTO notification_templates (
    event_type,
    channel,
    locale,
    version,
    active,
    title_template,
    content_template
)
VALUES (
    'ORDER_CREATED',
    'IN_APP',
    'vi',
    1,
    true,
    'Đơn hàng đã được tạo',
    'Đơn hàng {{orderCode}} của bạn đã được tạo thành công.'
);

--------------------------------------------------
-- ORDER_CREATED - EMAIL
--------------------------------------------------
INSERT INTO notification_templates (
    event_type,
    channel,
    locale,
    version,
    active,
    title_template,
    content_template
)
VALUES (
    'ORDER_CREATED',
    'EMAIL',
    'vi',
    1,
    true,
    'Xác nhận tạo đơn hàng',
    'Xin chào {{userName}}, đơn hàng {{orderCode}} của bạn đã được tạo.'
);

--------------------------------------------------
-- USER_REGISTERED - IN_APP
--------------------------------------------------
INSERT INTO notification_templates (
    event_type,
    channel,
    locale,
    version,
    active,
    title_template,
    content_template
)
VALUES (
    'USER_REGISTERED',
    'IN_APP',
    'vi',
    1,
    true,
    'Chào mừng bạn',
    'Chào mừng {{userName}} đến với hệ thống của chúng tôi.'
);

--------------------------------------------------
-- ORDER_CREATED
--------------------------------------------------
INSERT INTO notification_event_policy
(event_type, channel, mandatory, description)
VALUES
('ORDER_CREATED', 'IN_APP', true,  'Thông báo bắt buộc trong app'),
('ORDER_CREATED', 'EMAIL',  true,  'Email xác nhận đơn hàng');

--------------------------------------------------
-- USER_REGISTERED
--------------------------------------------------
INSERT INTO notification_event_policy
(event_type, channel, mandatory, description)
VALUES
('USER_REGISTERED', 'IN_APP', true, 'Thông báo chào mừng');