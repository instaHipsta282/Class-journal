INSERT INTO usr (id, active, activation_code, email, password, username, first_name, last_name, phone)
VALUES (2, FALSE, '123456', 'test@gmail.ru', '123', 'USER', 'Smith', 'Mr.', '89999999999');

INSERT INTO user_role (user_id, roles)
VALUES (2, 'USER')
ON CONFLICT (id) DO NOTHING;
