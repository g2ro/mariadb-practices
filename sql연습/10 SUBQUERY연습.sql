--
-- Subqyery
--

-- 1) SELECT 절, insert
SELECT 1+1
FROM dual;

SELECT (SELECT 1+1
		FROM dual) -- subquery
FROM dual;

-- insert into t1 values(null, (select max(no) + 1 from t1), );

--
-- 2) from 절의 서브쿼리
--
SELECT now() as n, sysdate() as s, 3 + 1 as r
FROM dual;

SELECT *
FROM (SELECT now() as n, sysdate() as s, 3 + 1 as r FROM dual) a;

--
-- 3) where 절의 서브쿼리
--

-- 예) 현재 Fai Bale이 근무하는 부서에서 근무하는 직원의 사번, 전체 이름을 출력해보세요.
SELECT b.dept_no
FROM employees a, dept_emp b
WHERE a.emp_no = b.emp_no
	AND b.to_date= "9999-01-01"
	AND concat(a.first_name, " ", a.last_name)= 'Fai Bale';

-- d004
SELECT a.emp_no, a.first_name 
FROM employees a, dept_emp b
WHERE a.emp_no = b.emp_no
	AND b.to_date= "9999-01-01"
    AND b.dept_no = 'd004';
    
SELECT a.emp_no, a.first_name 
FROM employees a, dept_emp b
WHERE a.emp_no = b.emp_no
	AND b.to_date= "9999-01-01"
    AND b.dept_no = (SELECT b.dept_no
					FROM employees a, dept_emp b
					WHERE a.emp_no = b.emp_no
						AND b.to_date= "9999-01-01"
						AND concat(a.first_name, " ", a.last_name)= 'Fai Bale');

-- 3-1) 단일행 연산자: =, >, <, >=, <=. <>, !=
-- 실습문제1
-- 현재, 전체 사원의 평균 연봉보다 적은 급여를 받는 사원의 이름과 급여를 출력하세요.
SELECT concat(a.first_name, " ", a.last_name) as '이름', b.salary
FROM employees a 
	JOIN salaries b ON a.emp_no = b.emp_no
WHERE b.to_date= "9999-01-01"
	AND b.salary < (SELECT avg(salary)
					FROM salaries
					where to_date= "9999-01-01")
ORDER BY 이름 asc;

-- 강사님 
SELECT avg(salary)
FROM salaries
WHERE to_date = '9999-01-01';

SELECT a.first_name, b.salary
FROM employees a, salaries b
WHERE a.emp_no = b.emp_no
	AND b.to_date= '9999-01-01'
    AND b.salary <( SELECT avg(salary)
					FROM salaries
					WHERE to_date = '9999-01-01')
ORDER BY b.salary desc;

-- 실습문제2
-- 현재, 직책별 평균급여 중에 가장 적은 평균급여의 직책이름과 그 평균급여를 출력하세요
SELECT title, avg(salary)
FROM salaries a
	JOIN titles b ON a.emp_no = b.emp_no
WHERE a.to_date='9999-01-01'
	AND b.to_date='9999-01-01'
GROUP BY b.title
ORDER BY avg(salary) asc
LIMIT 1;

-- 강사님
-- 1) 직책별 평균 급여
SELECT b.title, avg(a.salary)
FROM salaries a, titles b
WHERE a.emp_no = b.emp_no
	AND a.to_date= '9999-01-01'
    AND b.to_date= '9999-01-01'
GROUP BY b.title;

-- 2) 직책별 가장 적은 평균 급여
SELECT min(avg_salary)
FROM (SELECT b.title, avg(a.salary) as avg_salary
		FROM salaries a, titles b
		WHERE a.emp_no = b.emp_no
			AND a.to_date= '9999-01-01'
			AND b.to_date= '9999-01-01'
		GROUP BY b.title) a;
-- 3) sol1: where절 subquery(단일행 연산)
SELECT b.title, avg(a.salary)
FROM salaries a, titles b
WHERE a.emp_no = b.emp_no
	AND a.to_date= '9999-01-01'
    AND b.to_date= '9999-01-01'
GROUP BY b.title
HAVING avg(a.salary) = (SELECT min(avg_salary)
							FROM (SELECT b.title, avg(a.salary) as avg_salary
									FROM salaries a, titles b
									WHERE a.emp_no = b.emp_no
									AND a.to_date= '9999-01-01'
									AND b.to_date= '9999-01-01'
									GROUP BY b.title) a);
                                    
-- 4) sol2: top-k
SELECT b.title, avg(a.salary)
FROM salaries a, titles b
WHERE a.emp_no = b.emp_no
	AND a.to_date= '9999-01-01'
    AND b.to_date= '9999-01-01'
GROUP BY b.title
ORDER BY avg(a.salary) asc
	limit 1;


-- 3-2) 복수행 연산자: in, not in,  비교연산자 + any (ex, =any <>any), 비교연산자 + All (ex, >all, !=all)
-- any 사용법
-- 1. =any:
-- 2. >any, >=any: 최소값
-- 3. <any, <=any: 최대값
-- 4. <>any, !=any: not in

-- all 사용법
-- 1. =all: (x)
-- 2. >all, >=all: 최대값
-- 3. <all, <=all: 최소값
-- 4. <>all, !=all

-- 실습문제3
-- 현재 급여가 50000 아성안 직원의 이름과 급여를 출력하세요.
-- projection : 이름, 급여

SELECT a.first_name, b.salary
FROM employees a, salaries b
WHERE a.emp_no = b.emp_no
	AND b.to_date= '9999-01-01'
    AND b.salary > 50000
ORDER BY b.salary asc;
    
-- sol02
SELECT emp_no, salalry
FROM salaries
WHERE to_date="9999-01-01";

SELECT a.first_name, b.salary
FROM employees a, salaries b
WHERE a.emp_no = b.emp_no
	AND b.to_date= '9999-01-01'
    AND (a.emp_no, b.salary) in (SELECT emp_no, salary
								FROM salaries
								WHERE to_date="9999-01-01"
                                AND b.salary > 50000)
ORDER BY b.salary asc;

-- 실습문제 4:
-- 현재, 각 부서별 최고급여를 받고 있는 직원의 이름, 부서이름, 급여를 출력해 보세요.

-- 부서별 최고급여를 받고 있는 직원
SELECT de.dept_no, de.emp_no, max(s.salary)
FROM dept_emp de
	JOIN salaries s ON(de.emp_no = s.emp_no)
WHERE s.to_date="9999-01-01"
GROUP BY de.dept_no;

-- 직원의 이름 출력
SELECT a.first_name, c.dept_name, d.salary
FROM employees a
	JOIN dept_emp b ON (a.emp_no = b.emp_no)
    JOIN departments c ON (b.dept_no = c.dept_no)
    JOIN salaries d ON (d.emp_no = b.emp_no)
WHERE (c.dept_no, d.salary) in (SELECT de.dept_no, max(s.salary) as max_sal
								FROM dept_emp de
									JOIN salaries s ON(de.emp_no = s.emp_no)
								WHERE s.to_date="9999-01-01"
								GROUP BY de.dept_no);

-- 강사님
SELECT a.dept_no, max(b.salary)
FROM dept_emp a, salaries b
WHERE a.emp_no = b.emp_no
	AND a.to_date = '9999-01-01'
    AND b.to_date = '9999-01-01'
GROUP BY a.dept_no;

-- sol01: where in
SELECT c.dept_name, a.first_name, d.salary
FROM employees a, 
	dept_emp b, 
    departments c, 
    salaries d
WHERE a.emp_no = b.emp_no
	AND b.dept_no = c.dept_no
    AND a.emp_no = d.emp_no
	AND b.to_date = '9999-01-01'
    AND d.to_date = '9999-01-01'
    AND (b.dept_no, d.salary) in (SELECT a.dept_no, max(b.salary)
									FROM dept_emp a, salaries b
									WHERE a.emp_no = b.emp_no
										AND a.to_date = '9999-01-01'
										AND b.to_date = '9999-01-01'
									GROUP BY a.dept_no);

-- sol2: from절 subquery & join
SELECT c.dept_name, a.first_name, d.salary
FROM employees a, 
	dept_emp b, 
    departments c, 
    salaries d,
    (SELECT a.dept_no, max(b.salary) as max_salary
	FROM dept_emp a, salaries b
	WHERE a.emp_no = b.emp_no
		AND a.to_date = '9999-01-01'
		AND b.to_date = '9999-01-01'
	GROUP BY a.dept_no)e
WHERE a.emp_no = b.emp_no
	AND b.dept_no = c.dept_no
    AND a.emp_no = d.emp_no
	AND b.dept_no = e.dept_no
    AND b.to_date = '9999-01-01'
    AND d.to_date = '9999-01-01'
    AND d.salary = e.max_salary;