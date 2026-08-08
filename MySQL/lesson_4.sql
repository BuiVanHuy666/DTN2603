USE testing_system;

# 1. Join.
# Question 1: Viết lệnh để lấy ra danh sách nhân viên và thông tin phòng ban của họ
SELECT
    a.id,
    a.email,
    a.fullname,
    d.name
FROM `account` a
LEFT JOIN department d ON a.department_id = d.id;

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
LEFT JOIN position p ON a.position_id = p.id
WHERE a.position_id = (SELECT id FROM position WHERE name = 'DEV');

# Question 4: Viết lệnh để lấy ra danh sách các phòng ban có >3 nhân viên
SELECT
    d.name,
    COUNT(a.id) AS employee_count
FROM department d
JOIN `account` a ON a.department_id = d.id
GROUP BY d.id
HAVING COUNT(a.id) > 3;

# Question 5: Viết lệnh để lấy ra danh sách câu hỏi được sử dụng trong đề thi nhiều nhất
SELECT qu.id, qu.content, COUNT(eq.exam_id) AS exam_count
FROM question qu
JOIN exam_question eq ON qu.id = eq.question_id
GROUP BY qu.id
HAVING COUNT(eq.exam_id) = (
    SELECT MAX(exam_count)
    FROM (
             SELECT COUNT(exam_id) AS exam_count
             FROM exam_question
             GROUP BY question_id
         ) as temp_table
);

# Question 6: Thống kê mỗi category Question được sử dụng trong bao nhiêu Question
SELECT
    c.name,
    COUNT(c.id) AS question_count
FROM category_question c
LEFT JOIN question q ON q.category_id = c.id
GROUP BY c.id;

# Question 7: Thống kê mỗi Question được sử dụng trong bao nhiêu Exam
SELECT
    q.id,
    q.content,
    COUNT(eq.exam_id) AS use_times
FROM question q
LEFT JOIN exam_question eq ON eq.question_id = q.id
GROUP BY q.id;

# Question 8: Lấy ra Question có nhiều câu trả lời nhất
SELECT q.id, q.content, COUNT(a.id) AS answer_count
FROM question q
JOIN answer a ON q.id = a.question_id
GROUP BY q.id
HAVING COUNT(a.id) = (
    SELECT MAX(dem_cau_tra_loi)
    FROM (
             SELECT COUNT(id) AS dem_cau_tra_loi
             FROM answer
             GROUP BY question_id
         ) AS temp_tablew
);

# Question 9: Thống kê số lượng account trong mỗi group
SELECT
    g.name,
    COUNT(ga.account_id) AS account_count
FROM `group` g
LEFT JOIN group_account ga ON g.id = ga.group_id
GROUP BY g.id;

# Question 10: Tìm chức vụ có ít người nhất
SELECT p.id, p.name, COUNT(a.id) AS employee_count
FROM position p
LEFT JOIN `account` a ON p.id = a.position_id
GROUP BY p.id
HAVING COUNT(a.id) = (
    SELECT MIN(dem_nhan_vien)
    FROM (
             SELECT COUNT(a2.id) AS dem_nhan_vien
             FROM position p2
                      LEFT JOIN `account` a2 ON p2.id = a2.position_id
             GROUP BY p2.id
         ) AS temp
);

# Question 11: Thống kê mỗi phòng ban có bao nhiêu dev, test, scrum master, PM
SELECT
    d.id, d.name,
    COUNT(p.name = 'DEV') AS dev_count,
    COUNT(p.name = 'TEST') AS test_count,
    COUNT(p.name = 'SCRUM_MASTER') AS scrum_master_count,
    COUNT(p.name = 'PM') AS pm_count
FROM department d
LEFT JOIN account a ON d.id = a.department_id
LEFT JOIN position p ON a.position_id = p.id
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
LEFT JOIN type_question tq ON q.type_id = tq.id
LEFT JOIN account a ON q.creator_id = a.id
LEFT JOIN answer ans ON q.id = ans.question_id;

# Question 13: Lấy ra số lượng câu hỏi của mỗi loại tự luận hay trắc nghiệm
SELECT
    tq.name,
    COUNT(q.id) AS total_question
FROM type_question tq
LEFT JOIN question q ON tq.id = q.type_id
GROUP BY tq.id;

# Question 14: Lấy ra group không có account nào
# Question 15: Lấy ra group không có account nào
SELECT
    g.id,
    g.name
FROM `group` g
LEFT JOIN group_account ga ON g.id = ga.group_id
WHERE ga.account_id IS NULL;


# Question 16: Lấy ra question không có answer nào.
SELECT
    q.id,
    q.content
FROM question q
LEFT JOIN answer a ON q.id = a.question_id
WHERE a.id IS NULL;

# 2. Union.
# Question 17:
# a) Lấy các account thuộc nhóm thứ 1
SELECT a.id, a.email, a.fullname, ga.group_id
FROM `account` a
JOIN group_account ga ON a.id = ga.account_id
WHERE ga.group_id = 1;

# b) Lấy các account thuộc nhóm thứ 2
SELECT a.id, a.email, a.fullname, ga.group_id
FROM `account` a
JOIN group_account ga ON a.id = ga.account_id
WHERE ga.group_id = 2;

# c) Ghép 2 kết quả từ câu a) và câu b) sao cho không có record nào trùng nhau
SELECT a.id, a.email, a.fullname, ga.group_id
FROM `account` a
JOIN group_account ga ON a.id = ga.account_id
WHERE ga.group_id = 1
UNION
SELECT a.id, a.email, a.fullname, ga.group_id
FROM `account` a
JOIN group_account ga ON a.id = ga.account_id
WHERE ga.group_id = 2;

# Question 18:
# a) Lấy các group có lớn hơn 5 thành viên
SELECT g.id, g.name, COUNT(ga.account_id) AS member_count
FROM `group` g
JOIN group_account ga ON g.id = ga.group_id
GROUP BY g.id
HAVING COUNT(ga.account_id) > 5;

# b) Lấy các group có nhỏ hơn 7 thành viên
SELECT g.id, g.name, COUNT(ga.account_id) AS member_count
FROM `group` g
JOIN group_account ga ON g.id = ga.group_id
GROUP BY g.id
HAVING COUNT(ga.account_id) < 7;

# c) Ghép 2 kết quả từ câu a) và câu b)
SELECT g.id, g.name, COUNT(ga.account_id) AS member_count
FROM `group` g
JOIN group_account ga ON g.id = ga.group_id
GROUP BY g.id
HAVING COUNT(ga.account_id) > 5
UNION
SELECT g.id, g.name, COUNT(ga.account_id) AS member_count
FROM `group` g
JOIN group_account ga ON g.id = ga.group_id
GROUP BY g.id
HAVING COUNT(ga.account_id) < 7;
