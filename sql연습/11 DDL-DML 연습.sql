--
-- DDL/DML 연습
--
drop table member;

CREATE TABLE member(
	id int not null auto_increment,
    email varchar(200) not null,
    password varchar(64) not null,
    name varchar(50) not null,
    department varchar(100),
    
    primary key(id)
    
);
desc member;

-- alter

-- alter table member add column juminbunho char(13) not null default " ";
ALTER TABLE member ADD column juminbunho char(13) not null;
desc member;

ALTER TABLE member DROP juminbunho;
desc member;

ALTER TABLE member ADD column juminbunho char(13) not null after email; -- 	특정 컬럼 뒤에 추가하기
desc member;

ALTER TABLE member CHANGE COLUMN department dept varchar(100) not null; -- ALTER TABLE t1 CHANGE COLUMN old_col new col + condition
desc member;

ALTER TABLE member ADD COLUMN profile text;
desc member;

ALTER TABLE member DROP juminbunho;
desc member;

-- insert

INSERT INTO member
values(null, 'kickscar@gmail.com', password('1234'), '김길호', '개발팀', null);

SELECT *
FROM member;

INSERT INTO member(id, email, name, password, dept) -- 추천되는 insert방식
VALUES (null, "kilho@gmail.com", '김길호2', password('1234'), '개발팀');

SELECT *
FROM member;

-- update
UPDATE member
SET email= 'kilho3@gamil.com', password=password('12345')
WHERE id=2; -- update시 필수

SELECT *
FROM member;

-- delete
DELETE
FROM member
WHERE id =2;

SELECT *
FROM member;

-- transaction(tx)

SELECT id, email
FROM member;

SELECT @@autocommit; -- 1로 설정 
INSERT INTO member
values(null, 'kickscar2@gmail.com', '안대혁2', password('123'), '개발팀2' ,null);

-- tx:begin
set autocommit = 0;
SELECT @@autocommit; -- 0
INSERT INTO member
values(null, 'kickscar3@gmail.com', '안대혁3', password('123'), '개발팀3' ,null);
SELECT id, email
FROM member;
-- tx:end;
commit;
-- rollback;  --cache도 비워지면서 rollback
SELECT id, email
FROM member;

