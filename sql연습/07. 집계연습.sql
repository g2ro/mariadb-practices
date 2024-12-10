-- 1) 집계쿼리: SELECT 절에 집계함수(count, max, min, avg, variance, strdev, sum, ...)
SELECT avg(salary), SUM(salary) 
FROM salaries;

-- 2) select절에 집계함수(그룹함수)가 있는 경우, 어떤 컬럼도 select절에 올 수 없다.
-- emp_no는 아무 의미가 없다. 
-- 오류!
SELECT emp_no, SUM(salary) 
FROM salaries;

-- 3) query 순서
-- 1. from: 접근 테이블 확인
-- 2. where: 조건에 맞는 row를 확인(임시테이블)
-- 3. 집계(결과테이블)
-- 4. projection
SELECT SUM(salary) 
FROM salaries
WHERE emp_no = 10060;

-- 4) group by
-- group by에 참여하고 있는 컬럼은 projection이 가능하다 : select 절에 올 수 있다.
-- 예제 : 사원별 평균 연봉은?
SELECT avg(salary)
FROM salaries
GROUP BY emp_no;

-- 5) having
-- 집계결과(결과테이블)에서 row를 선택해야 하는 경우
-- 이미 where절은 실행이 되었기 때문에 having절에서 이 조건을 주어야 한다.
-- 예제: 평균월급이 60000 이상인 사원의 사번과 평균 연봉을 출력하세요.
SELECT avg(salary) as avg_salary
FROM salaries
GROUP BY emp_no
having avg_salary > 60000;

-- 6) oreder by
-- 		order by는 항상 맨 마지막 출력 전(projection)전에 한다.
SELECT avg(salary) as avg_salary
FROM salaries
GROUP BY emp_no
having avg_salary > 60000
order by avg_salary  asc;

-- 주의) 사번이 10060인 사번의 사번, 평균 급여, 급여 총합을 출력하세요

-- 문법적으로 옳지 않음
-- 의미적으로 맞음 (where)
SELECT emp_no, avg(salary), sum(salary)
FROM salaries
WHERE emp_no = 10060;

SELECT emp_no, avg(salary), sum(salary)
FROM salaries
GROUP BY emp_no
having emp_no = 10060;