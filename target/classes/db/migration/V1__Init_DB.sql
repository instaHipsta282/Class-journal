CREATE SEQUENCE hibernate_sequence START 2 INCREMENT 1;

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

CREATE SEQUENCE usr_id_seq START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS usr (
    id INT8 NOT NULL DEFAULT NEXTVAL('usr_id_seq'),
    activation_code VARCHAR(255),
    active BOOLEAN NOT NULL,
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    second_name VARCHAR(255),
    phone VARCHAR(16) NOT NULL,
    photo VARCHAR(255) NOT NULL DEFAULT 'default.jpg',
    PRIMARY KEY(id)
);

CREATE SEQUENCE course_id_seq START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS course (
                                   id INT8 NOT NULL DEFAULT NEXTVAL('course_id_seq'),
                                   title VARCHAR(255) NOT NULL,
                                   start_date DATE,
                                   end_date DATE,
                                   days_count INT4,
                                   students_count INT4 NOT NULL DEFAULT 0,
                                   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS course_usr (
                                      user_id INT8 NOT NULL REFERENCES usr,
                                      course_id INT8 NOT NULL REFERENCES course,
                                      PRIMARY KEY(user_id, course_id)
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

CREATE SEQUENCE role_id_seq START 1 INCREMENT 1;

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'user_role_fk') THEN
            ALTER TABLE IF EXISTS user_role
                ALTER COLUMN id
                    SET DEFAULT NEXTVAL('role_id_seq'),
                ADD CONSTRAINT user_role_fk
                    FOREIGN KEY (user_id) REFERENCES usr;
        END IF;
    END;
$$;

