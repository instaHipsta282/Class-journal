INSERT INTO usr (id, username, password, active)
    VALUES (999, 'ADMIN', '123', TRUE)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO user_role (user_id, roles)
    VALUES (999, 'USER'), (999, 'ADMIN')
    ON CONFLICT (user_id) DO NOTHING;