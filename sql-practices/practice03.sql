-- 테이블 조인(JOIN) SQL 문제입니다.

-- 문제 1. 
-- 현재 급여가 많은 직원부터 직원의 사번, 이름, 그리고 연봉을 출력 하시오.
SELECT a.emp_no as "사번", concat(a.first_name, " ", last_name) as "이름", b.salary as "급여"
FROM employees a 
	JOIN salaries b ON (a.emp_no = b.emp_no)
WHERE to_date= "9999-01-01"
ORDER BY 급여 desc;
-- 문제2.
-- 전체 사원의 사번, 이름, 현재 직책을 이름 순서로 출력하세요.
SELECT a.emp_no as "사번", concat(a.first_name, " ", last_name) as "이름" , b.title as "직책"
FROM employees a
	JOIN titles b ON (a.emp_no = b.emp_no)
WHERE b.to_date = "9999-01-01"
ORDER BY 이름 asc;

-- 문제3.
-- 전체 사원의 사번, 이름, 현재 부서를 이름 순서로 출력하세요.
SELECT a.emp_no as "사번", concat(a.first_name, " ", last_name) as "이름", dept_name as "부서"
FROM employees a
	JOIN dept_emp b ON (a.emp_no = b.emp_no)
    JOIN departments c ON (b.dept_no = c.dept_no)
WHERE b.to_date = "9999-01-01"
ORDER BY 이름 asc;

-- 문제4.
-- 현재 근무중인 전체 사원의 사번, 이름, 연봉, 직책, 부서를 모두 이름 순서로 출력합니다.
SELECT a.emp_no as "사번", concat(a.first_name, " ", last_name) as "이름", b.salary as "연봉", c.title as "직책", e.dept_name as "부서" 
FROM employees a
	JOIN salaries b ON (a.emp_no = b.emp_no)
    JOIN titles c ON (a.emp_no = c.emp_no)
    JOIN dept_emp d ON (a.emp_no = d.emp_no)
    JOIN departments e ON (d.dept_no = e.dept_no)
WHERE b.to_date= "9999-01-01"
	AND c.to_date= "9999-01-01"
    AND d.to_date= "9999-01-01"
ORDER BY 이름 asc;

-- 문제5.
-- 'Technique Leader'의 직책으로 과거에 근무한 적이 있는 모든 사원의 사번과 이름을 출력하세요.
-- (현재 'Technique Leader'의 직책으로 근무하는 사원은 고려하지 않습니다.)
SELECT a.emp_no as "사번", concat(a.first_name, " ", last_name) as "이름"
FROM employees a
	JOIN titles b ON(a.emp_no = b.emp_no)
WHERE b.to_date != "9999-01-01"
	AND title = "Technique Leader"
ORDER BY a.emp_no;

-- 문제6.
-- 직원 이름(last_name) 중에서 S로 시작하는 직원들의 이름, 부서명, 직책을 조회하세요.
SELECT concat(a.first_name, " ", last_name) as "이름", d.dept_name as "부서", b.title as "직책"
FROM employees a
	JOIN titles b ON (a.emp_no = b.emp_no)
    JOIN dept_emp c ON (a.emp_no = c.emp_no)
    JOIN departments d ON (c.dept_no = d.dept_no)
WHERE a.last_name like "S%"
	AND b.to_date = "9999-01-01";
-- 문제7.
-- 현재, 직책이 Engineer인 사원 중에서 현재 급여가 40,000 이상인 사원들의 사번, 이름, 급여 그리고 타이틀을 급여가 큰 순서대로 출력하세요.
SELECT a.emp_no as "사번", concat(a.first_name, " ", last_name) as "이름", c.salary as "급여"
FROM employees a
	JOIN titles b ON (a.emp_no = b.emp_no)
    JOIN salaries c ON (a.emp_no = c.emp_no)
WHERE b.to_date = "9999-01-01"
	AND c.to_date = "9999-01-01"
    AND b.title = 'Engineer'
    AND c.salary >- 40000
ORDER BY c.salary;
-- 문제8.
-- 현재, 평균급여가 50,000이 넘는 직책을 직책과 평균급여를 평균급여가 큰 순서대로 출력하세요.
SELECT a.title as "직책", avg(b.salary) as "평균급여"
FROM titles a
	JOIN salaries b ON (a.emp_no = b.emp_no)
WHERE a.to_date = "9999-01-01"
	AND b.to_date = "9999-01-01"
GROUP BY a.title
HAVING avg(b.salary) > 50000
ORDER BY avg(b.salary) desc;

-- 문제9.
-- 현재, 부서별 평균급여을 평균급여가 큰 순서대로 부서명과 평균연봉을 출력 하세요.
SELECT c.dept_name as "부서", avg(b.salary) as "평균연봉"
FROM dept_emp a
	JOIN salaries b ON (a.emp_no = b.emp_no)
    JOIN departments c ON (a.dept_no = c.dept_no)
GROUP BY c.dept_name
ORDER BY avg(b.salary) desc;


-- 문제10.
-- 현재, 직책별 평균급여를 평균급여가 큰 직책 순서대로 직책명과 그 평균연봉을 출력 하세요.
SELECT a.title as "직책", avg(b.salary) as "평균연봉"
FROM titles a
	JOIN salaries b ON (a.emp_no = b.emp_no)
WHERE a.to_date= "9999-01-01"
	AND b.to_date= "9999-01-01"
GROUP BY a.title
ORDER BY avg(b.salary)desc;