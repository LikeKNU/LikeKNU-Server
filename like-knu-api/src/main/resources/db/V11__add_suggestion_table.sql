CREATE TABLE IF NOT EXISTS suggestion
(
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content    VARCHAR(500) NOT NULL,
    created_at DATETIME     NOT NULL,
    device     VARCHAR(60),
    FOREIGN KEY (device) REFERENCES device (id) ON DELETE SET NULL ON UPDATE SET NULL
);
