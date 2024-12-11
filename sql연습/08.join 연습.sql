--
-- inner join
--

-- 예제1) 현재, 근무하고 있는 직원의 이름과 직책을 모두 출력하세요.
SELECT a.first_name, b.title
FROM employees a, titles b
WHERE a.emp_no = b.emp_no -- join 조건(n-1)
AND b.to_date = "9999-01-01"; -- row 선택조건

-- 예제2) 현재, 근무하고 있는 직원의 이름과 직책을 모두 출력하되 여성 엔지니어(Engineer)만 출력하세요
SELECT a.first_name, a.gender, b.title
FROM employees a, titles b
WHERE a.emp_no = b.emp_no -- join 조건(n-1)
AND b.to_date = "9999-01-01" -- row 선택조건
AND a.gender = 'f'		-- row 선택조건 2
AND b.title = 'Engineer'; -- row 선택조건 3

--
-- ANSI / ISO SQL1999 JOIN 표준 문법
--

-- 1) natural join => 잘 사용하지 않는다. 
--		조인 대싱이 되는 두 테이블에 이름이 같은 공통 컬럼이 있는 경우
-- 		조인 조건을 명시하지 않고 암묵적으로 조인이 된다.
SELECT a.first_name, b.title
FROM employees a 
	natural join titles b
WHERE b.to_date = "9999-01-01";

-- 2) join ~ using
-- natural join의 문제점
SELECT count(*)
FROM salaries a natural join titles b
WHERE a.to_date = "9999-01-01"
AND b.to_date = "9999-01-01";

SELECT count(*)
FROM salaries a join titles b using(emp_no)
WHERE a.to_date = "9999-01-01"
AND b.to_date = "9999-01-01";

-- 3) join ~ on
SELECT count(*)
FROM salaries a 
	join titles b ON a.emp_no = b.emp_no
WHERE a.to_date = "9999-01-01"
AND b.to_date = "9999-01-01";

-- 실습문제1. 현재, 직책별 평균 연봉을 큰 순서대로 출력하세요.
SELECT title, avg(b.salary)
FROM salaries a 
	join titles b ON a.emp_no = b.emp_no
WHERE a.to_date = "9999-01-01"
AND b.to_date = "9999-01-01"
GROUP BY b.title
ORDER BY avg(b.salary) desc;

-- 실습문제2
-- 현재, 직책별 평균 연봉과 직원수를 구하되 직원수가 100명 이상인 직책만 출력
-- projection: 직책 평균연봉 직원수
SELECT title as "직책" ,avg(salary) as "평균연봉", count(*) as "직원수"
FROM salaries a 
	JOIN titles b on a.emp_no = b.emp_no
WHERE a.to_date= "9999-01-01"
	AND b.to_date= "9999-01-01"
GROUP BY b.title
HAVING 직원수 >= 100
ORDER BY 직원수 asc;

-- 실습문제3
-- 현재, 부서별로 직책이 Engineer인 직원들에 대한 평균연봉을 구하세요.
-- projection: 부서이름 평균연봉
SELECT d.dept_name, avg(a.salary)
FROM salaries a
	JOIN titles b ON (a.emp_no = b.emp_no)
    JOIN dept_emp c ON(b.emp_no = c.emp_no)
    JOIN departments d ON (c.dept_no = d.dept_no)
WHERE a.to_date= "9999-01-01"
	AND b.to_date= "9999-01-01"
    AND b.title= "Engineer"
GROUP BY c.dept_no;

-- 강사님
SELECT a.dept_name, avg(d.salary)
FROM departments a, dept_emp b, titles c, salaries d
WHERE a.dept_no = b.dept_no
	AND b.emp_no = c.emp_no
    AND c.emp_no = d.emp_no
		AND b.to_date = "9999-01-01"
        AND c.to_date = "9999-01-01"
        AND d.to_date = "9999-01-01"
        AND c.title = "Engineer"
GROUP BY a.dept_name
ORDER BY avg(d.salary) desc;