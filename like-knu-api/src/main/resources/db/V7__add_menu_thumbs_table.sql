CREATE TABLE IF NOT EXISTS menu_thumbs
(
    id          BIGINT                            NOT NULL AUTO_INCREMENT,
    thumbs_type ENUM ('THUMBS_UP', 'THUMBS_DOWN') NOT NULL,
    thumbs_at   TIMESTAMP(3)                      NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    device      VARCHAR(36)                       NOT NULL,
    menu        VARCHAR(60)                       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (device) REFERENCES device (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (menu) REFERENCES menu (id) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE menu_thumbs
    ADD UNIQUE INDEX device_menu_uix (device, menu);
