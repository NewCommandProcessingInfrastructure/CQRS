UPDATE users_table
SET username = SUBSTRING(MD5(RAND()) FROM 1 FOR 10)
WHERE username IS NULL;