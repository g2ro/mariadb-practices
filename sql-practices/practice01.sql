-- 기본 SQL 문제

-- 문제1.
-- 사번이 10944인 사원의 이름은(전체 이름)
SELECT concat(first_name, ' ', last_name)
FROM employees
WHERE emp_no = 10944;

-- 문제2. 
-- 전체 직원의 다음 정보를 조회하세요. 가장 선임부터 출력이 되도록 하세요.
-- 출력은 이름, 성별, 입사일 순서이고 "이름", "성별", "입사일"로 컬럼 이름을 대체해 보세요.
SELECT concat(first_name, ' ', last_name) as '이름', gender as '성별', hire_date as '입사일'
FROM employees
ORDER BY hire_date asc;

-- 문제3.
-- 여직원과 남직원은 각 각 몇 명이나 있나요?(각각 쿼리 만들어서 각각 출력 또는 집계로 한 번에 해결)
SELECT gender, count(*) as count
FROM employees
GROUP BY gender;

SELECT count(*)
FROM employees
WHERE gender = 'M';

SELECT count(*)
FROM employees
WHERE gender = 'F';


-- 문제4.
-- 현재(to_date='9999-01-01'), 근무하고 있는 직원 수는 몇 명입니까? (salaries 테이블을 사용) 
SELECT count(*) 
FROM salaries
WHERE to_date like "9999-%";
   
-- 문제5.
-- 부서는 총 몇 개가 있나요?
SELECT count(distinct(title))
FROM titles;
  
-- 문제6.
-- 현재 부서 매니저는 몇 명이나 있나요?(역임 매너저는 제외)
SELECT count(distinct(emp_no))
FROM titles
WHERE title = 'Staff';
 
-- 문제7.
-- 전체 부서를 출력하려고 합니다. 순서는 부서이름이 긴 순서대로 출력해 보세요.
SELECT dept_name, length(dept_name) as dept_len
FROM departments
ORDER BY dept_len desc;

-- 문제8.
-- 현재 급여가 120,000이상 받는 사원은 몇 명이나 있습니까?
SELECT count(*) 
FROM salaries
WHERE to_date like "9999-%" and salary >= 120000;
   
-- 문제9.
-- 어떤 직책들이 있나요? 중복 없이 이름이 긴 순서대로 출력해 보세요.
SELECT distinct(title), length(title) as len_title
FROM titles
ORDER BY len_title desc;

-- 문제10
-- 현재 Engineer 직책의 사원은 총 몇 명입니까?
SELECT count(distinct(emp_no))
FROM titles
WHERE to_date like "9999-%"
	AND title = "Engineer";
   
-- 문제11
-- 사번이 13250(Zeydy)인 직원의 직책 변경 상황을 시간순으로 출력해보세요.
SELECT title, from_date
FROM titles
WHERE emp_no = 13250
ORDER BY from_date asc;

