USE testing_system;

INSERT INTO `position` (name) VALUES
('DEV'),
('TEST'),
('SCRUM_MASTER'),
('PM'),
('DEV'),
('TEST'),
('DEV'),
('PM'),
('TEST'),
('DEV');

INSERT INTO account (email, username, fullname, create_date, department_id, position_id) VALUES
('nguyen.van.an@gmail.com', 'nguyenvanan', 'Nguyễn Văn An', '2026-01-05 08:00:00', 1, 1),
('tran.thi.binh@gmail.com', 'tranthibinh', 'Trần Thị Bình', '2026-01-06 08:30:00', 2, 2),
('le.van.cuong@gmail.com', 'levancuong', 'Lê Văn Cường', '2026-01-07 09:00:00', 3, 3),
('pham.thi.dung@gmail.com', 'phamthidung', 'Phạm Thị Dung', '2026-01-08 09:15:00', 4, 4),
('hoang.van.em@gmail.com', 'hoangvanem', 'Hoàng Văn Em', '2026-01-09 08:45:00', 5, 1),
('vu.thi.phuong@gmail.com', 'vuthiphuong', 'Vũ Thị Phương', '2026-01-10 09:30:00', 6, 2),
('dang.van.hung@gmail.com', 'dangvanhung', 'Đặng Văn Hùng', '2026-01-11 08:20:00', 7, 1),
('bui.thi.hoa@gmail.com', 'buithihoa', 'Bùi Thị Hoa', '2026-01-12 10:00:00', 8, 4),
('do.van.khanh@gmail.com', 'dovankhanh', 'Đỗ Văn Khánh', '2026-01-13 08:10:00', 9, 2),
('ngo.thi.lan@gmail.com', 'ngothilan', 'Ngô Thị Lan', '2026-01-14 09:40:00', 10, 1);

INSERT INTO `group` (name, create_date, creator_id) VALUES
('Java Backend Team', '2026-01-15 08:00:00', 1),
('Frontend Team', '2026-01-15 08:30:00', 2),
('Testing Team', '2026-01-16 09:00:00', 3),
('Project Management', '2026-01-16 09:30:00', 4),
('Database Team', '2026-01-17 08:00:00', 5),
('DevOps Team', '2026-01-17 08:30:00', 6),
('Mobile Development', '2026-01-18 09:00:00', 7),
('Business Analysis', '2026-01-18 09:30:00', 8),
('Research Team', '2026-01-19 08:00:00', 9),
('Intern Team', '2026-01-19 08:30:00', 10);

INSERT INTO group_account (join_date, group_id, account_id) VALUES
('2026-01-20 08:00:00', 1, 1),
('2026-01-20 08:10:00', 1, 5),
('2026-01-20 08:20:00', 2, 2),
('2026-01-20 08:30:00', 2, 6),
('2026-01-20 08:40:00', 3, 3),
('2026-01-20 08:50:00', 3, 9),
('2026-01-20 09:00:00', 4, 4),
('2026-01-20 09:10:00', 5, 7),
('2026-01-20 09:20:00', 6, 8),
('2026-01-20 09:30:00', 7, 10);

INSERT INTO type_question (name) VALUES
('ESSAY'),
('MULTIPLE_CHOICE'),
('ESSAY'),
('MULTIPLE_CHOICE'),
('MULTIPLE_CHOICE'),
('ESSAY'),
('MULTIPLE_CHOICE'),
('ESSAY'),
('MULTIPLE_CHOICE'),
('ESSAY');

INSERT INTO category_question (name) VALUES
('Java'),
('SQL'),
('Database'),
('Spring Boot'),
('Testing'),
('Git'),
('HTML CSS'),
('JavaScript'),
('DevOps'),
('Software Engineering');

INSERT INTO question(content, create_date, creator_id, category_id, type_id) VALUES
(
'Java là gì và những đặc điểm chính của Java là gì?',
'2026-01-21 08:00:00',
1,
1,
1
),
(
'Từ khóa nào được sử dụng để kế thừa một class trong Java?',
'2026-01-21 08:10:00',
2,
1,
2
),
(
'PRIMARY KEY trong SQL dùng để làm gì?',
'2026-01-21 08:20:00',
3,
2,
2
),
(
'Foreign Key là gì? Hãy giải thích mục đích của Foreign Key.',
'2026-01-21 08:30:00',
4,
3,
1
),
(
'Spring Boot được xây dựng dựa trên framework nào?',
'2026-01-21 08:40:00',
5,
4,
2
),
(
'Hãy trình bày sự khác nhau giữa Unit Test và Integration Test.',
'2026-01-21 08:50:00',
6,
5,
1
),
(
'Git command nào được sử dụng để tạo một repository mới?',
'2026-01-21 09:00:00',
7,
6,
2
),
(
'Hãy giải thích khái niệm Responsive Web Design.',
'2026-01-21 09:10:00',
8,
7,
1
),
(
'HTTP method nào thường được sử dụng để tạo resource mới?',
'2026-01-21 09:20:00',
9,
8,
2
),
(
'CI/CD là gì và tại sao CI/CD quan trọng trong phát triển phần mềm?',
'2026-01-21 09:30:00',
10,
9,
1
);

INSERT INTO answer(content, is_correct, question_id) VALUES
('Java là ngôn ngữ lập trình hướng đối tượng, đa nền tảng.', TRUE, 1),
('Java sử dụng từ khóa extends để kế thừa class.', TRUE, 2),
('Java sử dụng từ khóa inherit để kế thừa class.', FALSE, 2),

('PRIMARY KEY dùng để định danh duy nhất mỗi record trong bảng.', TRUE, 3),
('PRIMARY KEY dùng để lưu dữ liệu dạng TEXT.', FALSE, 3),

('Foreign Key dùng để tạo mối quan hệ giữa các bảng.', TRUE, 4),

('Spring Boot được xây dựng dựa trên Spring Framework.', TRUE, 5),
('Spring Boot được xây dựng dựa trên React.', FALSE, 5),

('Unit Test kiểm thử từng đơn vị nhỏ, Integration Test kiểm thử sự tương tác giữa nhiều thành phần.', TRUE, 6),

('git init được sử dụng để tạo Git repository mới.', TRUE, 7),

('Responsive Web Design giúp website hiển thị phù hợp trên nhiều kích thước màn hình.', TRUE, 8),

('POST thường được sử dụng để tạo resource mới.', TRUE, 9),
('GET thường được sử dụng để tạo resource mới.', FALSE, 9),

('CI/CD giúp tự động hóa quá trình build, test và deployment.', TRUE, 10);

INSERT INTO exam (code, title, duration, create_date, category_id, creator_id) VALUES
('EXAM001', 'Java Core Basic', 60, '2026-01-22 08:00:00', 1, 1),
('EXAM002', 'SQL Basic', 45, '2026-01-22 08:30:00', 2, 2),
('EXAM003', 'Database Fundamentals', 60, '2026-01-22 09:00:00', 3, 3),
('EXAM004', 'Spring Boot Basic', 60, '2026-01-22 09:30:00', 4, 4),
('EXAM005', 'Software Testing', 45, '2026-01-22 10:00:00', 5, 5),
('EXAM006', 'Git Fundamentals', 30, '2026-01-22 10:30:00', 6, 6),
('EXAM007', 'Frontend Basic', 45, '2026-01-22 11:00:00', 7, 7),
('EXAM008', 'JavaScript Basic', 45, '2026-01-22 11:30:00', 8, 8),
('EXAM009', 'DevOps Basic', 60, '2026-01-22 13:00:00', 9, 9),
('EXAM010', 'Software Engineering', 90, '2026-01-22 13:30:00', 10, 10);

INSERT INTO exam_question(exam_id, question_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(6, 7),
(7, 8),
(8, 9),
(9, 10);

# Question 2: lấy ra tất cả các phòng ban
SELECT * FROM department;

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
WHERE duration >= 60 AND create_date < '2019-12-20';

# Question 9: Lấy ra 5 group được tạo gần đây nhất
SELECT *
FROM `group`
ORDER BY create_date DESC
LIMIT 5;

# Question 10: Đếm số nhân viên thuộc department có id = 2
SELECT COUNT(*)
FROM account
WHERE department_id = 2;

# Question 11: Lấy ra nhân viên có tên bắt đầu bằng chữ "D" và kết thúc bằng chữ "o"
SELECT *
FROM account
WHERE fullname LIKE 'D%o';

# Question 12: Xóa tất cả các exam được tạo trước ngày 20/12/2019
DELETE FROM exam
WHERE create_date < '2019-12-20';

# Question 13: Xóa tất cả các question có nội dung bắt đầu bằng từ "câu hỏi"
DELETE FROM question
WHERE content LIKE 'câu hỏi%';

# Question 14: Update thông tin của account có id = 5 thành tên "Nguyễn Bá Lộc" và email thành loc.nguyenba@vti.com.vn
UPDATE account
SET fullname = 'Nguyễn Bá Lộc', email = 'loc.nguyenba@vti.com.vn'
WHERE id = 5;

# Question 15: update account có id = 5 sẽ thuộc group có id = 4
UPDATE group_account
SET group_id = 4
WHERE account_id = 5;
