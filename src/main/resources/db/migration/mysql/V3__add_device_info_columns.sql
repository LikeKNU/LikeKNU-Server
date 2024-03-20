ALTER TABLE device
    ADD COLUMN theme_color VARCHAR(7) DEFAULT 'light';

ALTER TABLE device
    ADD COLUMN favorite_cafeteria VARCHAR(60);
