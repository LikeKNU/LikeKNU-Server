CREATE TABLE IF NOT EXISTS user_log
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    device      VARCHAR(36),
    log_type    ENUM ('SEARCH_ANNOUNCEMENT', 'CLICK_ANNOUNCEMENT', 'SELECT_SHUTTLE')
                       NOT NULL,
    `value`     VARCHAR(255),
    `timestamp` TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (id),
    INDEX ix_timestamp (`timestamp`)
);