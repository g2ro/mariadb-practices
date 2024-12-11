-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 단 조회결과는 급여의 내림차순으로 정렬되어 나타나야 합니다. 

-- 문제1.
-- 현재 전체 사원의 평균 급여보다 많은 급여를 받는 사원은 몇 명이나 있습니까?
SELECT count(*) as "평균 급여 이상인 사원수"
FROM salaries
WHERE to_date = "9999-01-01"
	AND salary > ( SELECT avg(salary)
					FROM salaries
                    WHERE to_date= "9999-01-01");
    
-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.

-- 각 부서별 최고 급액을 뽑아 내자
SELECT dept_no, max(salary)
FROM dept_emp a
	JOIN salaries b ON (a.emp_no = b.emp_no)
WHERE a.to_date= "9999-01-01"
	AND b.to_date= "9999-01-01"
GROUP BY dept_no
ORDER BY b.salary desc;

-- 2번 정답
SELECT a.emp_no as "사번", concat(a.first_name, " ", a.last_name) as "이름", c.dept_name as "부서", d.salary as "급여"
FROM employees a
	JOIN dept_emp b ON (a.emp_no = b.emp_no)
    JOIN departments c ON (b.dept_no = c.dept_no)
    JOIN salaries d ON (a.emp_no = d.emp_no)
WHERE (b.dept_no, d.salary) in (SELECT dept_no, max(salary)
								FROM dept_emp a
									JOIN salaries b ON (a.emp_no = b.emp_no)
								WHERE a.to_date= "9999-01-01"
									AND b.to_date= "9999-01-01"
								GROUP BY dept_no)
	AND b.to_date= "9999-01-01"
    AND d.to_date= "9999-01-01"
ORDER BY 급여 desc;

-- 문제3.
-- 현재, 사원 자신들의 부서의 평균급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요 

-- 부서별 평균 급여 출력
SELECT b.dept_no, avg(a.salary)
FROM salaries a
	JOIN dept_emp b ON (a.emp_no = b.emp_no)
WHERE a.to_date= "9999-01-01"
	AND b.to_date= "9999-01-01"
GROUP BY b.dept_no;

-- 3번 정답
SELECT a.emp_no as "사번", concat(a.first_name, " ", a.last_name) as "이름", c.salary as "급여"
FROM employees a
	JOIN dept_emp b ON(a.emp_no = b.emp_no)
    JOIN salaries c ON(a.emp_no = c.emp_no)
WHERE b.to_date= "9999-01-01"
	AND c.to_date= "9999-01-01"
	AND c.salary > (SELECT avg(d.salary)
					FROM salaries d
						JOIN dept_emp e ON(d.emp_no = e.emp_no)
					WHERE d.to_date = "9999-01-01"
						AND e.to_date = "9999-01-01"
                        AND b.dept_no = e.dept_no)
ORDER BY 급여 desc;
					
-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.
    
-- 4번 정답
SELECT 
    a.emp_no AS "사번", 
    CONCAT(a.first_name, " ", a.last_name) AS "이름", 
    mgr.manager_name AS "매니저 이름", 
    c.dept_name AS "부서"
FROM employees a
	JOIN dept_emp b ON (a.emp_no = b.emp_no)
	JOIN departments c ON (b.dept_no = c.dept_no)
	JOIN (
		SELECT 
			dept.dept_no, 
			CONCAT(emp.first_name, " ", emp.last_name) AS manager_name
		FROM dept_manager dept
			JOIN employees emp ON dept.emp_no = emp.emp_no
		WHERE dept.to_date = "9999-01-01") mgr ON (mgr.dept_no = b.dept_no)
WHERE b.to_date = "9999-01-01"
ORDER BY 이름;

-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.

-- 평균 급여가 가장 높은 부서를 찾자
SELECT a.dept_no
FROM dept_emp a
	JOIN salaries b ON (a.emp_no = b.emp_no)
WHERE b.to_date= "9999-01-01"
GROUP BY a.dept_no
ORDER BY avg(salary) desc
LIMIT 1;

-- 5번 정답
SELECT emp.emp_no as '사번', concat(emp.first_name, " ", emp.last_name) as "이름", t.title as "직책", s.salary as "급여"
FROM employees as emp
	JOIN dept_emp as dep ON(emp.emp_no = dep.emp_no)
    JOIN titles as t ON (emp.emp_no = t.emp_no)
    JOIN salaries as s on(emp.emp_no = s.emp_no)
WHERE dep.dept_no = (SELECT a.dept_no
				FROM dept_emp a
					JOIN salaries b ON (a.emp_no = b.emp_no)
				WHERE b.to_date= "9999-01-01"
				GROUP BY a.dept_no
				ORDER BY avg(salary) desc
				LIMIT 1)
	AND dep.to_date= "9999-01-01"
    AND t.to_date= "9999-01-01"
    AND s.to_date= "9999-01-01"
ORDER BY 급여;

-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.
SELECT c.dept_name as "부서이름", avg(salary) as "평균급여"
FROM dept_emp a
	JOIN salaries b ON (a.emp_no = b.emp_no)
    JOIN departments c ON(c.dept_no = a.dept_no)
WHERE b.to_date= "9999-01-01"
GROUP BY a.dept_no
ORDER BY avg(salary) desc
LIMIT 1;

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.
SELECT title, avg(salary)
FROM titles a
	JOIN salaries b ON(a.emp_no = b.emp_no)
WHERE a.to_date= "9999-01-01"
	AND b.to_date= "9999-01-01"
GROUP BY a.title
ORDER BY avg(salary) desc
LIMIT 1;