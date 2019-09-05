INSERT INTO usr (username, password, active)
    VALUES ('ADMIN', '123', TRUE)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO user_role (user_id, roles)
    VALUES (1, 'USER'), (1, 'ADMIN')
    ON CONFLICT (id) DO NOTHING;

