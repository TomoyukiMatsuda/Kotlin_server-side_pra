-- TODO: 何故かうまくいかない、DB作成されてない気がする
INSERT INTO users(name, email, password, role)
SELECT * FROM (
 SELECT
  'admin',
  'test@example.com',
  '$2a$10$zkWc793o2fA1eHIDpudpKuCY9HBhWIz2dSTvvERSdsajfq7uybja2',
  0
) AS tbl
WHERE NOT EXISTS (SELECT 1 FROM users);