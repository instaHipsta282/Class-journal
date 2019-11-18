INSERT INTO usr (id, active, activation_code, email, password, username, first_name, last_name, second_name, phone)
VALUES (2, FALSE, '123456', 'test@gmail.ru', '123', 'Test', 'firstName', 'lastName', 'secondName', '89999999999');

INSERT INTO course_usr(user_id, course_id)
VALUES (2, 1);