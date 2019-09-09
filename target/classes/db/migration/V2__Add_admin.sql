INSERT INTO usr (id, active, password, username, first_name, last_name, phone)
    VALUES (1, TRUE, '123', 'ADMIN', 'Poll', 'Anderson', '89999999999')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO user_role (user_id, roles)
    VALUES (1, 'USER'), (1, 'ADMIN')
    ON CONFLICT (id) DO NOTHING;

