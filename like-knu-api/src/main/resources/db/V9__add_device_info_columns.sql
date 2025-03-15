ALTER TABLE device
    ADD COLUMN model_name VARCHAR(32) AFTER platform;

ALTER TABLE device
    ADD COLUMN os_version VARCHAR(12) AFTER model_name;
