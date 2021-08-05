DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users(name, email, password)
VALUES ('User', 'user@email.com', 'password'),
       ('Admin', 'admin@email.com', 'password');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'USER'),
       (100001, 'ADMIN');