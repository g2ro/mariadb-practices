--
-- join
--

-- 예제: 이름이 'parto hitomi'인 직원의 현재 직책를 구하세요.
SELECT emp_no, last_name
FROM employees
WHERE concat(first_name, " ", last_name) = 'parto hitomi';

-- 11052
SELECT title
FROM titles
WHERE emp_no = 11052;

SELECT a.emp_no, a.first_name, a.last_name, b.title
FROM employees a, titles b
WHERE a.emp_no = b.emp_no -- join condition
	and concat(a.first_name, " ", a.last_name) = 'parto hitomi'; -- row selection
