ALTER TABLE cafeteria
    MODIFY COLUMN cafeteria_name VARCHAR(20);

ALTER TABLE cafeteria
    ADD COLUMN `sequence` INT;

UPDATE cafeteria
SET cafeteria_name = '직원식당'
WHERE cafeteria_name = 'EMPLOYEE_CAFETERIA';

UPDATE cafeteria
SET cafeteria_name = '학생식당'
WHERE cafeteria_name = 'STUDENT_CAFETERIA';

UPDATE cafeteria
SET cafeteria_name = '생활관식당'
WHERE cafeteria_name = 'DORMITORY';

UPDATE cafeteria
SET cafeteria_name = '늘솜'
WHERE cafeteria_name = 'NEULSOM';

UPDATE cafeteria
SET cafeteria_name = '소담'
WHERE cafeteria_name = 'SODAM';

UPDATE cafeteria
SET cafeteria_name = '은행사/비전'
WHERE cafeteria_name = 'EUNHAENGSA_VISION';

UPDATE cafeteria
SET cafeteria_name = '드림'
WHERE cafeteria_name = 'DREAM';

ALTER TABLE cafeteria
    MODIFY COLUMN cafeteria_name VARCHAR(20);
