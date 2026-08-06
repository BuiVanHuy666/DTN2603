USE testing_system;

# Question 1: Viết lệnh để lấy ra danh sách nhân viên và thông tin phòng ban của họ
SELECT
    a.id,
    a.email,
    a.fullname,
    d.name
FROM `account` a
LEFT JOIN department d
ON a.department_id = d.id;

# Question 2: Viết lệnh để lấy ra thông tin các account được tạo sau ngày 20/12/2010
SELECT *
FROM `account`
WHERE create_date > '2010-12-20';

# Question 3: Viết lệnh để lấy ra tất cả các developer
SELECT
    a.id,
    a.email,
    a.fullname
FROM `account` a
LEFT JOIN position p
ON a.position_id = p.id
WHERE a.position_id = (SELECT id FROM position WHERE name = 'DEV');

# Question 4: Viết lệnh để lấy ra danh sách các phòng ban có >3 nhân viên
SELECT
    d.name,
    COUNT(a.id) AS employee_count
FROM department d
JOIN `account` a
ON a.department_id = d.id
GROUP BY d.id
HAVING COUNT(a.id) > 3;

# Question 5: Viết lệnh để lấy ra danh sách câu hỏi được sử dụng trong đề thi nhiều nhất
SELECT
    qu.content,
    COUNT(qu.id) AS exam_count
FROM question qu
JOIN exam_question eq
ON qu.id = eq.question_id
GROUP BY qu.id
ORDER BY exam_count DESC;

# Question 6: Thống kê mỗi category Question được sử dụng trong bao nhiêu Question
SELECT
    c.name,
    COUNT(c.id) AS question_count
FROM category_question c
LEFT JOIN question q
ON q.category_id = c.id
GROUP BY c.id;

# Question 7: Thống kê mỗi Question được sử dụng trong bao nhiêu Exam
SELECT
    q.id,
    q.content,
    COUNT(eq.exam_id) AS use_times
FROM question q
LEFT JOIN exam_question eq
ON eq.question_id = q.id
GROUP BY q.id;

# Question 8: Lấy ra Question có nhiều câu trả lời nhất
SELECT
    q.id,
    q.content,
    COUNT(q.id) AS answer_count
FROM question q
JOIN answer a
ON q.id = a.question_id
GROUP BY q.id
ORDER BY answer_count DESC
LIMIT 1;

# Question 9: Thống kê số lượng account trong mỗi group
SELECT
    g.name,
    COUNT(ga.account_id) AS account_count
FROM `group` g
LEFT JOIN group_account ga
ON g.id = ga.group_id
GROUP BY g.id;

# Question 10: Tìm chức vụ có ít người nhất
SELECT
    p.name,
    COUNT(a.id) AS employee_count
FROM position p
LEFT JOIN `account` a
ON a.position_id = p.id
GROUP BY p.id
ORDER BY employee_count
LIMIT 1;

# Question 11: Thống kê mỗi phòng ban có bao nhiêu dev, test, scrum master, PM
SELECT
    d.id, d.name,
    COUNT(p.name = 'DEV') AS dev_count,
    COUNT(p.name = 'TEST') AS test_count,
    COUNT(p.name = 'SCRUM_MASTER') AS scrum_master_count,
    COUNT(p.name = 'PM') AS pm_count
FROM department d
LEFT JOIN account a
ON d.id = a.department_id
LEFT JOIN position p
ON a.position_id = p.id
GROUP BY d.id;

# Question 12: Lấy thông tin chi tiết của câu hỏi bao gồm: thông tin cơ bản của question, loại câu hỏi, ai là người tạo ra câu hỏi, câu trả lời là gì, …
SELECT
    q.id,
    q.content,
    q.create_date,
    tq.name AS question_type,
    a.fullname AS creator,
    ans.id AS answer_id,
    ans.content AS answer_content,
    ans.is_correct
FROM question q
LEFT JOIN type_question tq
ON q.type_id = tq.id
LEFT JOIN account a
ON q.creator_id = a.id
LEFT JOIN answer ans
ON q.id = ans.question_id;

# Question 13: Lấy ra số lượng câu hỏi của mỗi loại tự luận hay trắc nghiệm
SELECT
    tq.name,
    COUNT(q.id) AS total_question
FROM type_question tq
LEFT JOIN question q
ON tq.id = q.type_id
GROUP BY tq.id;

# Question 14: Lấy ra group không có account nào
# Question 15: Lấy ra group không có account nào
SELECT
    g.id,
    g.name
FROM `group` g
LEFT JOIN group_account ga
ON g.id = ga.group_id
WHERE ga.account_id IS NULL;


# Question 16: Lấy ra question không có answer nào.
SELECT
    q.id,
    q.content
FROM question q
LEFT JOIN answer a
ON q.id = a.question_id
WHERE a.id IS NULL;
