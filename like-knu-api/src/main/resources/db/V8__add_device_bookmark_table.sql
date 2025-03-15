CREATE TABLE IF NOT EXISTS device_bookmark
(
    device       VARCHAR(36) NOT NULL,
    announcement VARCHAR(60) NOT NULL,
    PRIMARY KEY (device, announcement),
    FOREIGN KEY (device) REFERENCES device (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (announcement) REFERENCES announcement (id) ON DELETE CASCADE ON UPDATE CASCADE
);
