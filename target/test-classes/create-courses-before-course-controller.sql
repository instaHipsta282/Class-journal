INSERT INTO course (id, title, start_date, end_date, days_count, students_count, students_limit)
VALUES (1, 'Present', '2019-11-16', '2020-01-01', 12, 1, 5);

INSERT INTO course (id, title, start_date, end_date, days_count, students_count, students_limit)
VALUES (2, 'NotActually', '2019-09-03', '2019-09-15', 12, 1, 5);

INSERT INTO course (id, title, start_date, end_date, days_count, students_count, students_limit)
VALUES (3, 'Future', '2019-12-01', '2019-12-15', 12, 1, 5);

INSERT INTO course_usr(user_id, course_id)
VALUES (1, 1);

INSERT INTO course_usr(user_id, course_id)
VALUES (1, 2);
