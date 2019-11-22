INSERT INTO usr (id, active, email, password, username, first_name, last_name, phone)
VALUES (1, TRUE, 'stepaden@mail.ru', '123', 'ADMIN', 'Poll', 'Anderson', '89999999999'),
       (2, TRUE, 'test@gmail.ru', '123', 'USER', 'Smith', 'Mr.', '89999999999');

INSERT INTO user_role (user_id, roles)
VALUES (1, 'USER'), (1, 'ADMIN'), (2, 'USER');

CREATE EXTENSION IF NOT EXISTS PGCRYPTO;

UPDATE usr
SET password = CRYPT(password, GEN_SALT('BF', 8));
