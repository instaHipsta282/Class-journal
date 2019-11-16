INSERT INTO usr (id, active, password, username, first_name, last_name, phone)
    VALUES (1, TRUE, '123', 'ADMIN', 'Poll', 'Anderson', '89999999999')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO user_role (user_id, roles)
    VALUES (1, 'USER'), (1, 'ADMIN')
    ON CONFLICT (id) DO NOTHING;


-- INSERT INTO course (id, title, start_date, end_date, days_count, students_count)
--     VALUES (1, 'Algorithmic', '2019-09-03', '2019-09-15', 12, 31);
--
--
-- INSERT INTO course (id, title, start_date, end_date, days_count, students_count)
--     VALUES (2, 'Computer science', '2019-10-15', '2019-10-29', 14, 13);
--
-- INSERT INTO course (id, title, start_date, end_date, days_count, students_count)
--     VALUES (3, 'Machine learning', '2019-11-01', '2019-11-29', 28, 12);
