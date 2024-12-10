--
-- 문자열 함수
--

-- upper
SELECT upper('seoul'), ucase('SeouL')
FROM dual;

SELECT upper(first_name) from employees;

-- lower
SELECT lower('seoul'), lcase('Seoul')
FROM dual;

SELECT lower(first_name)
FROM employees;

-- substring(문자열, index, length)
SELECT substring("hello world",3,2) 
FROM dual; -- 1부터 시작

-- 예제:  emoloyees 테이블에서 1989 년에 입사한 직원의 이름, 입사일 출력
SELECT first_name, hire_date
FROM employees
WHERE substring(hire_date, 1,4) = '1989';

-- lpad, rpad
SELECT lpad('1234', 10, '-'), rpad('1234', 10, '-')
FROM dual;

-- trim, ltrin, rtrim
SELECT concat('-----' , ltrim('     hello    '), '-----')
	,concat('-----' , ltrim('    hello    '), '-----')
    ,concat('-----' , trim(leading "x" from 'xxxxxhelloxxxxx'), '-----')
    ,concat('-----' , trim(trailing "x" from 'xxxxxhelloxxxxx'), '-----')
    ,concat ('-----' , trim(both "x" from 'xxxxxhelloxxxxx'), '-----')
    
SELECT length("Hello World")
FROM dual;

