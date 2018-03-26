INSERT INTO accounts (username, password) VALUES
  ('user', '$2a$04$6pnKYG.Y4fnjoARUhJVzy.QRnyHcoJIAxtfqfEvyFrcsH7Iuy6cIi' /*123456*/),
  ('admin', '$2a$04$6pnKYG.Y4fnjoARUhJVzy.QRnyHcoJIAxtfqfEvyFrcsH7Iuy6cIi' /*123456*/);

INSERT INTO accounts_roles
  SELECT
    a.id,
    r.id
  FROM accounts AS a, roles AS r
  WHERE a.username = 'user' AND r.name = 'ROLE_USER';

INSERT INTO accounts_roles
  SELECT
    a.id,
    r.id
  FROM accounts AS a, roles AS r
  WHERE a.username = 'admin' AND r.name = 'ROLE_ADMIN';