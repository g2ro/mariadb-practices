--
-- select 기본 
--

-- 예제1. departments 테이블의 모든 컬럼 출력
SELECT * FROM departments;

-- projection
-- 예제 2. employee 테이블에서 직원의 이름, 성별, 입사일을 출력
SELECT first_name, gender, hire_date
FROM employees;

-- as(alias, 생략가능)
-- 예제3. employees 테이블에서 직원의 이름, 성별, 입사일 출력
SELECT first_name as '이름',
	gender as '성별',
    hire_date as '입사일'
FROM employees as a;

SELECT concat(first_name,' ',last_name) as '이름',
	gender as '성별',
    hire_date as '입사일'
FROM employees as a;

-- distinct
-- 예제4. 직급 이름을 한 번씩만 출력하기 => 우리 화사는 어떤 직급이 있나요?
SELECT title
FROM titles;

SELECT distinct(title)
FROM titles;

-- 
-- where
--

-- 비교연산
-- 예제 5. employees 테이블에서 1991년 이전에 입사한 직원의 이름, 성별, 입사일 출력
SELECT first_name,
	gender,
    hire_date
FROM employees
WHERE hire_date < '1991-01-01';
-- 논리연산
-- 예제 6. employees 테이블에서 1989년 이전에 입사한 여직원의 이름, 입사일을 출력
SELECT first_name,
	gender,
    hire_date
FROM employees
WHERE hire_date < '1991-01-01'
	AND gender = 'f';
-- in 연산자
-- 예제7: dept_emp 테이블에서 부서 번호가 d005나 d009에 속하는 사원의 사번, 부서번호 출력
SELECT emp_no, dept_no
FROM dept_emp
WHERE dept_no = 'd005'
	or dept_no = 'doo9';
    
SELECT emp_no, dept_no
FROM dept_emp
WHERE dept_no in ('d005', 'doo9');
-- like 검색
-- 예제8 : employees 테이블에서 1989년에 입사한 직원의 이름, 입사일을 출력
SELECT first_name, hire_date
FROM employees
WHERE '1989-01-01' <= hire_date <= '1989-12-31';

SELECT first_name, hire_date
FROM employees
WHERE '1989-01-01' <= hire_date 
	and hire_date <= '1989-12-31';
    
SELECT first_name, hire_date
FROM employees
WHERE hire_date between '1989-01-01' and '1989-12-31';

SELECT first_name, hire_date
FROM employees
WHERE hire_date like '1989-%';

--
-- ORDER BY
--

-- 예제9 : employees 테이블에서 직원의 이름, 성별, 입사일을 입사일 순으로 출력
SELECT first_name, gender, hire_date
FROM employees
ORDER BY hire_date asc;

-- 예제10: salaries 테이블에서 2001년 월급이 가장 높은순으로 사번, 월급 출력
SELECT emp_no, from_date, to_date, salary
FROM salaries
where  from_date LIKE '2001%'
	and to_date LIKE '2001%'
ORDER BY salary desc;

-- 예제 11: 직원의 사번과 월급을 사번(asc),월급(desc) 순으로 출력
SELECT emp_no, salary
FROM salaries
ORDER BY emp_no asc, salary desc;
