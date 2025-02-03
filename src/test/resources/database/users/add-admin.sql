INSERT INTO users(email, password, first_name, last_name)
VALUES ('admin@test.com',
        '$2a$10$Gsc1L3hFyuNEUNTOvvRipOohYnV16Vsa3ezaDFbYcWLJWhY9N56Su',
        'Admin',
        'Admin');

INSERT INTO users_roles(user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'admin@test.com'),
        (SELECT id FROM roles WHERE name = 'ADMIN'));
