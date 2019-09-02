CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS message (
    id INT8 NOT NULL,
    filename VARCHAR(255),
    tag VARCHAR(255),
    text VARCHAR(2048) NOT NULL,
    user_id INT8,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS user_role (
    id INT8 NOT NULL,
    user_id INT8 NOT NULL,
    roles VARCHAR(255),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS usr (
    id INT8 NOT NULL,
    activation_code VARCHAR(255),
    active BOOLEAN NOT NULL,
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'message_user_fk') THEN
            ALTER TABLE IF EXISTS message
                ADD CONSTRAINT message_user_fk
                    FOREIGN KEY (user_id) REFERENCES usr;
        END IF;
    END;
$$;

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'user_role_fk') THEN
            ALTER TABLE IF EXISTS user_role
                ADD CONSTRAINT user_role_fk
                    FOREIGN KEY (user_id) REFERENCES usr;
        END IF;
    END;
$$;

