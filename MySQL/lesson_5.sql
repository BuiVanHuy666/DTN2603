-- Question 1: Tạo view có chứa danh sách nhân viên thuộc phòng ban sale
# CTE
WITH SaleDepartment AS (
    SELECT id
    FROM department
    WHERE name = 'Sale'
)
SELECT a.*
FROM `account` a
JOIN SaleDepartment sd ON a.department_id = sd.id;

# View
CREATE OR REPLACE VIEW v_sale_employees AS
SELECT a.*
FROM `account` a
JOIN department d ON a.department_id = d.id
WHERE d.id = (
    SELECT id
    FROM department
    WHERE name = 'sale'
);

SELECT * FROM v_sale_employees;

-- Question 2: Tạo view có chứa thông tin các account tham gia vào nhiều group nhất
# CTE
WITH
CountData AS (
    SELECT account_id, COUNT(group_id) AS total_group
    FROM group_account
    GROUP BY account_id
),
MaxCount AS (
     SELECT MAX(total_group) AS max_value
     FROM CountData
)
SELECT a.id, a.fullname, cd.total_group
FROM `account` a
JOIN CountData cd ON a.id = cd.account_id
JOIN MaxCount mc ON cd.total_group = mc.max_value;

# View
CREATE OR REPLACE VIEW v_max_group_accounts AS
SELECT a.id, a.fullname, COUNT(ga.group_id) AS total_group
FROM `account` a
JOIN group_account ga ON a.id = ga.account_id
GROUP BY a.id
HAVING total_group >= ALL (
    SELECT COUNT(group_id)
    FROM group_account
    GROUP BY account_id
);


SELECT * FROM v_max_group_accounts;

-- Question 3: Tạo view có chứa câu hỏi có những content quá dài (content quá 300 từ được coi là quá dài) và xóa nó đi
# CTE
WITH LongQuestions AS (
    SELECT *
    FROM question
    WHERE (LENGTH(content) - LENGTH(REPLACE(content, ' ', '')) + 1) > 300
)
SELECT * FROM LongQuestions;

# View
CREATE OR REPLACE VIEW v_long_questions AS
SELECT *
FROM question
WHERE (LENGTH(content) - LENGTH(REPLACE(content, ' ', '')) + 1) > 300;

DELETE FROM v_long_questions;

-- Question 4: Tạo view có chứa danh sách các phòng ban có nhiều nhân viên nhất
# CTE
WITH
EmployeeCount AS (
    SELECT department_id, COUNT(id) AS total_employee
    FROM `account`
    GROUP BY department_id
),
MaxEmployee AS (
 SELECT MAX(total_employee) AS max_value
 FROM EmployeeCount
)
SELECT d.id, d.name, ec.total_employee
FROM department d
JOIN EmployeeCount ec ON d.id = ec.department_id
JOIN MaxEmployee me ON ec.total_employee = me.max_value;

#View
CREATE OR REPLACE VIEW v_max_employee_departments AS
SELECT d.id, d.name, COUNT(a.id) AS total_employee
FROM department d
         JOIN `account` a ON d.id = a.department_id
GROUP BY d.id
HAVING COUNT(a.id) >= ALL (
    SELECT COUNT(id)
    FROM `account`
    GROUP BY department_id
);

SELECT * FROM v_max_employee_departments;

-- Question 5: Tạo view có chứa tất các các câu hỏi do user họ Nguyễn tạo.

# CTE
WITH NguyenCreators AS (
    SELECT id
    FROM `account`
    WHERE fullname LIKE 'Nguyễn %'
)
SELECT q.*
FROM question q
JOIN NguyenCreators nc ON q.creator_id = nc.id;

# View
CREATE OR REPLACE VIEW v_nguyen_creator_questions AS
SELECT q.*
FROM question q
JOIN `account` a ON q.creator_id = a.id
WHERE a.fullname LIKE 'Nguyễn %';

SELECT * FROM v_nguyen_creator_questions;