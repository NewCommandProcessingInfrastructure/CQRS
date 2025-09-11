UPDATE users_table_additional
SET username = SUBSTRING(MD5(RAND()) FROM 1 FOR 10)
WHERE username IS NULL;