--
-- 날짜 함수
-- 

--
SELECT curdate(), current_date()
FROM dual;

SELECT curtime(), current_time
FROM dual;

-- now() vs sysdate()
SELECT now(), sysdate()
FROM dual;

SELECT now(), sleep(2), now() from dual;
SELECT now(), sleep(2), sysdate() from dual;

-- date_format
-- default format
-- date : %Y-%m-%d
-- datetime %Y-%m-%d %h:%i:%s

SELECT now()
FROM dual;

SELECT date_format(now(), "%y년 %m월 %d일 %h시 %i분 %s초")
FROM dual;

SELECT date_format(now(), "%d %b \'%y %h:%i:%s초")
FROM dual;

-- period_diff
-- 예제: 근무 개월
--	YYMM, YYYYMM
SELECT first_name, 
	hire_date,
    period_diff(date_format(curdate(), "%y%m"), date_format(hire_date, "%y%m")) as '근무개월'
FROM employees;

-- date_add(=addDate), date_sub(=subdate)
-- 예제: 각 사원의 근속 연수가 5년이 되는 날에 휴가를 보내준다면 각 사원의 5년 근속 휴가 날짜는?
SELECT first_name, 
	hire_date,
    date_add(hire_date, interval 5 year)
FROM employees;
