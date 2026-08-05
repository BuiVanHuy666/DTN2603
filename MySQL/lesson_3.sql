USE testing_system;

INSERT INTO department (name)
VALUES ('Sale'),
       ('Marketing'),
       ('IT'),
       ('HR'),
       ('Finance');

INSERT INTO `position` (name)
VALUES ('DEV'),
       ('TEST'),
       ('SCRUM_MASTER'),
       ('PM');

INSERT INTO `account` (email, username, fullname, create_date, department_id, position_id)
VALUES ('acc1@gmail.com', 'user1', 'Nguyen Van A', '2019-12-21', 3, 1),
       ('acc2@gmail.com', 'user2', 'Tran Van B', '2020-01-15', 3, 1),
       ('acc3@gmail.com', 'user3', 'Le Thi C', '2009-12-20', 3, 2),
       ('acc4@gmail.com', 'user4', 'Pham Van D', '2021-05-10', 3, 2),
       ('acc5@gmail.com', 'user5', 'Hoang Van E', '2021-06-12', 1, 3),
       ('acc6@gmail.com', 'user6', 'Ngo Thi F', '2022-07-22', 1, 4),
       ('acc7@gmail.com', 'user7', 'Vu Van G', '2010-12-19', 2, 1),
       ('acc8@gmail.com', 'user8', 'Bui Thi H', '2023-01-01', 2, 4);

INSERT INTO `group` (name, create_date, creator_id)
VALUES ('Nhom 1', '2021-01-01', 1),
       ('Nhom 2', '2021-02-01', 2),
       ('Nhom Khong Nguoi', '2021-03-01', 3);

INSERT INTO group_account (join_date, group_id, account_id)
VALUES ('2021-01-02', 1, 1),
       ('2021-01-02', 1, 2),
       ('2021-01-02', 1, 3),
       ('2021-01-02', 1, 4),
       ('2021-01-02', 1, 5),
       ('2021-01-02', 1, 6),
       ('2021-02-02', 2, 1),
       ('2021-02-02', 2, 7),
       ('2021-02-02', 2, 8);

INSERT INTO type_question (name)
VALUES ('ESSAY'),
       ('MULTIPLE_CHOICE');

INSERT INTO category_question (name)
VALUES ('Java'),
       ('SQL'),
       ('C++'),
       ('Ruby');

INSERT INTO question (content, create_date, creator_id, category_id, type_id)
VALUES ('Cau hoi Java 1', '2021-04-01', 1, 1, 1),
       ('Cau hoi SQL 1', '2021-04-02', 2, 2, 2),
       ('Cau hoi C++ 1', '2021-04-03', 3, 3, 1),
       ('Cau hoi SQL 2 (Chua co dap an)', '2021-04-04', 1, 2, 2);

INSERT INTO answer (content, is_correct, question_id)
VALUES ('Dap an 1 cho cau 1', true, 1),
       ('Dap an 2 cho cau 1', false, 1),
       ('Dap an 3 cho cau 1', false, 1),
       ('Dap an 1 cho cau 2', true, 2),
       ('Dap an 2 cho cau 2', false, 2),
       ('Dap an 1 cho cau 3', true, 3);

INSERT INTO exam (code, title, duration, create_date, category_id, creator_id)
VALUES ('EXAM01', 'De thi Java', 60, '2021-05-01', 1, 1),
       ('EXAM02', 'De thi SQL', 45, '2021-05-02', 2, 2),
       ('EXAM03', 'De thi C++', 90, '2021-05-03', 3, 3);

INSERT INTO exam_question (exam_id, question_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (3, 3);

# Question 2: lấy ra tất cả các phòng ban
SELECT *
FROM department;

# Question 3: lấy ra id của phòng ban "Sale"
SELECT id
FROM department
WHERE name = 'Sale';

# Question 4: lấy ra thông tin account có full name dài nhất
SELECT *
FROM `account`
ORDER BY LENGTH(fullname) DESC
LIMIT 1;

# Question 5: Lấy ra thông tin account có full name dài nhất và thuộc phòng ban có id= 3
SELECT *
FROM `account`
WHERE department_id = 3
ORDER BY LENGTH(fullname) DESC
LIMIT 1;

# Question 6: Lấy ra tên group đã tham gia trước ngày 20/12/2019
SELECT name
FROM `group`
WHERE create_date < '2019-12-20';

# Question 7: Lấy ra ID của question có >= 4 câu trả lời
SELECT question_id
FROM answer
GROUP BY question_id
HAVING COUNT(*) >= 4;

# Question 8: Lấy ra các mã đề thi có thời gian thi >= 60 phút và được tạo trước ngày 20/12/2019
SELECT code
FROM exam
WHERE duration >= 60
  AND create_date < '2019-12-20';

# Question 9: Lấy ra 5 group được tạo gần đây nhất
SELECT *
FROM `group`
ORDER BY create_date DESC
LIMIT 5;

# Question 10: Đếm số nhân viên thuộc department có id = 2
SELECT COUNT(*)
FROM `account`
WHERE department_id = 2;

# Question 11: Lấy ra nhân viên có tên bắt đầu bằng chữ "D" và kết thúc bằng chữ "o"
SELECT *
FROM `account`
WHERE fullname LIKE 'D%o';

# Question 12: Xóa tất cả các exam được tạo trước ngày 20/12/2019
DELETE
FROM exam
WHERE create_date < '2019-12-20';

# Question 13: Xóa tất cả các question có nội dung bắt đầu bằng từ "câu hỏi"
DELETE
FROM question
WHERE content LIKE 'câu hỏi%';

# Question 14: Update thông tin của account có id = 5 thành tên "Nguyễn Bá Lộc" và email thành loc.nguyenba@vti.com.vn
UPDATE `account`
SET fullname = 'Nguyễn Bá Lộc',
    email    = 'loc.nguyenba@vti.com.vn'
WHERE id = 5;

# Question 15: update account có id = 5 sẽ thuộc group có id = 4
UPDATE group_account
SET group_id = 4
WHERE account_id = 5;
