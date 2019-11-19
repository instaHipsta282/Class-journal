ALTER SEQUENCE schedule_id_seq RESTART WITH 100;

INSERT INTO course (id, title, start_date, end_date, days_count, students_count, students_limit)
VALUES (1, 'Present', '2019-11-16', '2019-11-20', 5, 1, 5);

INSERT INTO course (id, title, start_date, end_date, days_count, students_count, students_limit)
VALUES (3, 'Future', '2019-12-01', '2019-12-06', 6, 1, 5);

INSERT INTO course_usr(user_id, course_id)
VALUES (1, 1);

INSERT INTO course_usr(user_id, course_id)
VALUES (1, 3);

INSERT INTO schedule (id, date, presence_status, score, course_id, user_id)
VALUES (1, '2019-11-16', 'NONE', 'NONE', 1, 1),
       (2, '2019-11-17', 'NONE', 'NONE', 1, 1),
       (3, '2019-11-18', 'NONE', 'NONE', 1, 1),
       (4, '2019-11-19', 'NONE', 'NONE', 1, 1),
       (5, '2019-11-20', 'NONE', 'NONE', 1, 1);

INSERT INTO schedule (id, date, presence_status, score, course_id, user_id)
VALUES (6, '2019-12-01', 'NONE', 'NONE', 3, 1),
       (7, '2019-12-02', 'NONE', 'NONE', 3, 1),
       (8, '2019-12-03', 'NONE', 'NONE', 3, 1),
       (9, '2019-12-04', 'NONE', 'NONE', 3, 1),
       (10, '2019-12-05', 'NONE', 'NONE', 3, 1),
       (11, '2019-12-06', 'NONE', 'NONE', 3, 1);