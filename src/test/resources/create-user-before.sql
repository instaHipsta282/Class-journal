ALTER SEQUENCE user_id_seq RESTART WITH 2;
INSERT INTO usr (id, active, activation_code, email, password, username, first_name, last_name, second_name, phone)
VALUES (3, FALSE, '123456', 'test@gmail.ru', '123', 'Test', 'firstName', 'lastName', 'secondName', '89999999999');

