desc emaillist;

-- insert
INSERT INTO emaillist
VALUES (null,'둘','리', 'dooly@gmail.con');
-- list
SELECT
	id, first_name, last_name, email
FROM emaillist
ORDER BY id desc;

-- delete
DELETE 
FROM emaillist
WHERE id = 1;