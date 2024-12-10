--
-- 	수학 함수
--

-- abs
SELECT abs(1), abs(-1)
FROM dual;

-- ceil
SELECT ceil(3.14), ceiling(3.14)
FROM dual;

-- floor
SELECT floor(3.14)
FROM dual;

-- mod
SELECT mod(10,3), 10 % 3
FROM dual;

-- round(x): x에 가장 근접한 정수
-- round(x, d): x값 중에 소수점 d자리에 가장 근접한 실수
SELECT round(1.498), round(1.501), round(1.498, 1), round(1.498,0)
FROM dual;

-- power(x, y), pow(x, y) : x의 y승
SELECT power(2,10), pow(2,10)
FROM dual;

-- sign(x) : 양수 1, 음수 -1, 0 -> 0
SELECT sign(20), sign(-100), sign(0)
FROM dual;

-- greatest(x,y, ...), least(x,y, ...)
SELECT greatest(10,40,20,50), least(10,40,20,50)
FROM dual;

SELECT greatest('b','A', 'C', 'D'), least('hello', 'hela', 'hell')
FROM dual;