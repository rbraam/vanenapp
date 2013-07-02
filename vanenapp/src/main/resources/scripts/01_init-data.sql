INSERT INTO user_(
            name, password, username)
    VALUES ('Admin user', 'admin','admin');
INSERT INTO user_role(
user_,roles)
    VALUES ((SELECT id from user_ where username='admin' LIMIT 1), 'SUPERADMIN');

