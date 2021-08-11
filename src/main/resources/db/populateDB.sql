DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users(name, email, password)
VALUES ('User', 'user@email.com', 'password'),
       ('Admin', 'admin@email.com', 'password');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'USER'),
       (100001, 'ADMIN');

INSERT INTO meals(user_id, date_time, description, calories)
VALUES (100000, '2021-01-30 10:00:00', 'Breakfast', 500),
       (100000, '2021-01-30 13:00:00', 'Lunch', 1000),
       (100000, '2021-01-30 20:00:00', 'Dinner', 500),
       (100000, '2021-01-31 00:00:00', 'Midnight eat', 100),
       (100000, '2021-01-31 10:00:00', 'Breakfast', 1000),
       (100000, '2021-01-31 13:00:00', 'Lunch', 500),
       (100000, '2021-01-31 20:00:00', 'Dinner', 410),
       (100001, '2021-07-31 14:00:00', 'Admin lunch', 510),
       (100001, '2021-07-31 21:00:00', 'Admin dinner', 1500);