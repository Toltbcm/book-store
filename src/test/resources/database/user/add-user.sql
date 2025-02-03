INSERT INTO users(email, password, first_name, last_name)
VALUES ('user@test.com',
        '$2a$10$x20r0iQOxqSyYGcSfIi8vuYSZVFINWuudPIuBzFsH9Lx9VrbIO0Q.',
        'User',
        'User');

INSERT INTO users_roles(user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'user@test.com'),
        (SELECT id FROM roles WHERE name = 'USER'));
