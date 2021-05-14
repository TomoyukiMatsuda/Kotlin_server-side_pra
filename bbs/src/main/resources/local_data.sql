INSERT INTO users(name, email, password, role)
SELECT * FROM (
 SELECT
  'admin',
  'test@example.com',
  '$2a$10$qMp0fJAXka8ON3Yb5wrTaOCphErbaKyVWpAUufydkGGm6wsEGPOuK',
  'ADMIN'
) AS tbl
WHERE NOT EXISTS (SELECT 1 FROM users);