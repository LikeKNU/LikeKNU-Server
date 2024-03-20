CREATE TABLE IF NOT EXISTS academic_calendar
(
    id         VARCHAR(60) NOT NULL
        PRIMARY KEY,
    contents   VARCHAR(30) NOT NULL,
    start_date DATE        NOT NULL,
    end_date   DATE        NOT NULL,
    CONSTRAINT schedule_unique
        UNIQUE (contents, start_date, end_date)
);

CREATE TABLE IF NOT EXISTS announcement
(
    id                 VARCHAR(60)                                                                                                  NOT NULL
        PRIMARY KEY,
    announcement_title VARCHAR(70)                                                                                                  NOT NULL,
    announcement_url   VARCHAR(200)                                                                                                 NOT NULL,
    announcement_date  DATE                                                                                                         NOT NULL,
    campus             ENUM ('ALL', 'SINGWAN', 'CHEONAN', 'YESAN')                                                                  NOT NULL,
    category           ENUM ('STUDENT_NEWS', 'LIBRARY', 'DORMITORY', 'INTERNSHIP')                                                  NOT NULL,
    tag                ENUM ('ENROLMENT', 'DORMITORY', 'SCHOLARSHIP', 'TUITION', 'WORK', 'LIBRARY', 'MILEAGE', 'INTERNSHIP', 'ETC') NOT NULL,
    collected_at       DATETIME                                                                                                     NOT NULL,
    CONSTRAINT announcement_unique
        UNIQUE (announcement_url, announcement_date, category, announcement_title)
);

CREATE TABLE IF NOT EXISTS cafeteria
(
    id                VARCHAR(60)                                                                                                     NOT NULL
        PRIMARY KEY,
    cafeteria_name    ENUM ('STUDENT_CAFETERIA', 'EMPLOYEE_CAFETERIA', 'EUNHAENGSA_VISION', 'DREAM', 'DORMITORY', 'SODAM', 'NEULSOM') NOT NULL,
    weekday_breakfast VARCHAR(15)                                                                                                     NULL,
    weekday_lunch     VARCHAR(15)                                                                                                     NULL,
    weekday_dinner    VARCHAR(15)                                                                                                     NULL,
    weekend_breakfast VARCHAR(15)                                                                                                     NULL,
    weekend_lunch     VARCHAR(15)                                                                                                     NULL,
    weekend_dinner    VARCHAR(15)                                                                                                     NULL,
    campus            ENUM ('ALL', 'SINGWAN', 'CHEONAN', 'YESAN')                                                                     NOT NULL
);

CREATE TABLE IF NOT EXISTS city_bus
(
    id          VARCHAR(60)      NOT NULL
        PRIMARY KEY,
    bus_number  VARCHAR(10)      NOT NULL,
    bus_name    VARCHAR(60)      NULL,
    bus_color   VARCHAR(7)       NULL,
    bus_stop    VARCHAR(30)      NOT NULL,
    is_realtime BIT DEFAULT b'0' NULL
);

CREATE TABLE IF NOT EXISTS bus_time
(
    bus_id       VARCHAR(60) NOT NULL,
    arrival_time TIME        NOT NULL,
    PRIMARY KEY (bus_id, arrival_time),
    CONSTRAINT bus_time_ibfk_1
        FOREIGN KEY (bus_id) REFERENCES city_bus (id)
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS device
(
    id              VARCHAR(36)                                 NOT NULL
        PRIMARY KEY,
    fcm_token       VARCHAR(200)                                NULL,
    campus          ENUM ('ALL', 'SINGWAN', 'CHEONAN', 'YESAN') NOT NULL,
    notification    TINYINT(1) DEFAULT 0                        NOT NULL,
    registered_at   DATETIME                                    NOT NULL,
    platform        VARCHAR(300)                                NULL,
    last_visited_at DATETIME                                    NULL,
    CONSTRAINT fcm_token
        UNIQUE (fcm_token)
);

CREATE TABLE IF NOT EXISTS main_header_message
(
    id            BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    message       VARCHAR(16) NOT NULL,
    registered_at DATETIME    NOT NULL
)
    COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS menu
(
    id           VARCHAR(60)                           NOT NULL
        PRIMARY KEY,
    menus        VARCHAR(300)                          NULL,
    meal_type    ENUM ('BREAKFAST', 'LUNCH', 'DINNER') NOT NULL,
    menu_date    DATE                                  NOT NULL,
    cafeteria_id VARCHAR(60)                           NOT NULL,
    CONSTRAINT menu_ibfk_1
        FOREIGN KEY (cafeteria_id) REFERENCES cafeteria (id)
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notification
(
    id                 VARCHAR(60)          NOT NULL
        PRIMARY KEY,
    notification_title VARCHAR(80)          NOT NULL,
    notification_body  VARCHAR(150)         NOT NULL,
    notification_date  DATETIME             NOT NULL,
    notification_url   VARCHAR(1000)        NOT NULL,
    `read`             TINYINT(1) DEFAULT 0 NOT NULL
);

CREATE TABLE IF NOT EXISTS device_notification
(
    device_id       VARCHAR(36) NOT NULL,
    notification_id VARCHAR(60) NOT NULL,
    PRIMARY KEY (device_id, notification_id),
    CONSTRAINT device_notification_ibfk_1
        FOREIGN KEY (device_id) REFERENCES device (id)
            ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT device_notification_ibfk_2
        FOREIGN KEY (notification_id) REFERENCES notification (id)
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX notification_id
    ON device_notification (notification_id);

CREATE TABLE IF NOT EXISTS route
(
    id             VARCHAR(60)                                 NOT NULL
        PRIMARY KEY,
    route_type     ENUM ('OUTGOING', 'INCOMING')               NOT NULL,
    departure_stop VARCHAR(30)                                 NOT NULL,
    arrival_stop   VARCHAR(30)                                 NOT NULL,
    origin         VARCHAR(20)                                 NOT NULL,
    destination    VARCHAR(20)                                 NOT NULL,
    campus         ENUM ('ALL', 'SINGWAN', 'CHEONAN', 'YESAN') NOT NULL,
    sequence       INT                                         NOT NULL
);

CREATE TABLE IF NOT EXISTS bus_route
(
    bus_id   VARCHAR(60) NOT NULL,
    route_id VARCHAR(60) NOT NULL,
    PRIMARY KEY (bus_id, route_id),
    CONSTRAINT bus_route_ibfk_1
        FOREIGN KEY (bus_id) REFERENCES city_bus (id)
            ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT bus_route_ibfk_2
        FOREIGN KEY (route_id) REFERENCES route (id)
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shuttle
(
    id           VARCHAR(60)                         NOT NULL
        PRIMARY KEY,
    origin       VARCHAR(20)                         NOT NULL,
    destination  VARCHAR(20)                         NOT NULL,
    shuttle_type ENUM ('SCHOOL_BUS', 'CIRCULAR_BUS') NOT NULL,
    note         VARCHAR(30)                         NULL
);

CREATE TABLE IF NOT EXISTS shuttle_bus
(
    id         VARCHAR(60) NOT NULL
        PRIMARY KEY,
    bus_name   VARCHAR(20) NOT NULL,
    shuttle_id VARCHAR(60) NOT NULL,
    CONSTRAINT shuttle_bus_ibfk_1
        FOREIGN KEY (shuttle_id) REFERENCES shuttle (id)
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX shuttle_id
    ON shuttle_bus (shuttle_id);

CREATE TABLE IF NOT EXISTS shuttle_campus
(
    shuttle_id VARCHAR(60)                                 NOT NULL,
    campus     ENUM ('ALL', 'SINGWAN', 'CHEONAN', 'YESAN') NOT NULL,
    PRIMARY KEY (shuttle_id, campus),
    CONSTRAINT shuttle_campus_ibfk_1
        FOREIGN KEY (shuttle_id) REFERENCES shuttle (id)
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shuttle_time
(
    bus_id       VARCHAR(60) NOT NULL,
    arrival_stop VARCHAR(30) NOT NULL,
    arrival_time TIME        NOT NULL,
    sequence     INT         NOT NULL,
    PRIMARY KEY (bus_id, arrival_stop, arrival_time),
    CONSTRAINT shuttle_time_ibfk_1
        FOREIGN KEY (bus_id) REFERENCES shuttle_bus (id)
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subscribe
(
    device_id VARCHAR(36)                                                                                                  NOT NULL,
    tag       ENUM ('ENROLMENT', 'DORMITORY', 'SCHOLARSHIP', 'TUITION', 'WORK', 'LIBRARY', 'MILEAGE', 'INTERNSHIP', 'ETC') NOT NULL,
    PRIMARY KEY (device_id, tag),
    CONSTRAINT subscribe_ibfk_1
        FOREIGN KEY (device_id) REFERENCES device (id)
            ON UPDATE CASCADE ON DELETE CASCADE
);

