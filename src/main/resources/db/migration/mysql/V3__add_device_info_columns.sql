ALTER TABLE device
    ADD COLUMN theme_color VARCHAR(7) DEFAULT 'light';

ALTER TABLE device
    ADD COLUMN favorite_cafeteria VARCHAR(60);

ALTER TABLE device
    ADD FOREIGN KEY (favorite_cafeteria) REFERENCES cafeteria (id)
        ON DELETE CASCADE ON UPDATE CASCADE;