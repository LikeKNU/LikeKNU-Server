CREATE TABLE release_version
(
    id           BIGINT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    version      VARCHAR(8) NOT NULL,
    release_date DATE       NOT NULL
);
