# Tạo DB Testing System.
CREATE DATABASE IF NOT EXISTS testing_system;

USE testing_system;

# Table 1:Department
CREATE TABLE IF NOT EXISTS department
(
    id   BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256)
);

# Table 2: Position
CREATE TABLE IF NOT EXISTS `position`
(
    id   BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name ENUM ('DEV', 'TEST', 'SCRUM_MASTER', 'PM')
);

# Table 3: Account
CREATE TABLE IF NOT EXISTS `account`
(
    id            BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    email         VARCHAR(256) UNIQUE,
    username      VARCHAR(256) UNIQUE,
    fullname      VARCHAR(256),
    create_date   DATETIME NOT NULL,

    department_id BIGINT UNSIGNED,
    position_id   BIGINT UNSIGNED,
    CONSTRAINT fk_account_department FOREIGN KEY (department_id) REFERENCES `department` (id),
    CONSTRAINT fk_account_position FOREIGN KEY (position_id) REFERENCES `position` (id)
);

# Table 4: Group
CREATE TABLE IF NOT EXISTS `group`
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(256),
    create_date DATETIME,

    creator_id  BIGINT UNSIGNED,
    CONSTRAINT fk_group_creator FOREIGN KEY (creator_id) REFERENCES account (id)
);

# Table 5: GroupAccount
CREATE TABLE IF NOT EXISTS group_account
(
    join_date  DATETIME,

    group_id   BIGINT UNSIGNED,
    account_id BIGINT UNSIGNED,
    PRIMARY KEY (group_id, account_id),
    CONSTRAINT fk_group_account_group FOREIGN KEY (group_id) REFERENCES `group` (id),
    CONSTRAINT fk_group_account_account FOREIGN KEY (account_id) REFERENCES `account` (id)
);

# Table 6: TypeQuestion
CREATE TABLE IF NOT EXISTS type_question
(
    id   BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name ENUM ('ESSAY', 'MULTIPLE_CHOICE')
);

# Table 7: CategoryQuestion
CREATE TABLE IF NOT EXISTS category_question
(
    id   BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256)
);

# Table 8: Question
CREATE TABLE IF NOT EXISTS question
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    content     TEXT,
    create_date DATETIME,

    creator_id  BIGINT UNSIGNED NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,
    type_id     BIGINT UNSIGNED NOT NULL,
    CONSTRAINT fk_question_category FOREIGN KEY (category_id) REFERENCES category_question (id),
    CONSTRAINT fk_question_type FOREIGN KEY (type_id) REFERENCES type_question (id),
    CONSTRAINT fk_question_creator FOREIGN KEY (creator_id) REFERENCES account (id)
);

# Table 9: Answer
CREATE TABLE IF NOT EXISTS answer
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    content     TEXT,
    is_correct  BOOLEAN,

    question_id BIGINT UNSIGNED NOT NULL,
    CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES question (id)
);

# Table 10: Exam
CREATE TABLE IF NOT EXISTS exam
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    code        VARCHAR(256) UNIQUE,
    title       VARCHAR(256),
    duration    INT,
    create_date DATETIME,

    category_id BIGINT UNSIGNED NOT NULL,
    creator_id  BIGINT UNSIGNED NOT NULL,
    CONSTRAINT fk_exam_category FOREIGN KEY (category_id) REFERENCES category_question (id),
    CONSTRAINT fk_exam_creator FOREIGN KEY (creator_id) REFERENCES account (id)
);

# Table 11: ExamQuestion
CREATE TABLE IF NOT EXISTS exam_question
(
    exam_id     BIGINT UNSIGNED NOT NULL,
    question_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (exam_id, question_id),
    CONSTRAINT fk_exam_question_exam FOREIGN KEY (exam_id) REFERENCES exam (id),
    CONSTRAINT fk_exam_question_question FOREIGN KEY (question_id) REFERENCES question (id)
);

# Insert data into tables
INSERT INTO department (name)
VALUES ('Sale'),
       ('Marketing'),
       ('BOD'),
       ('IT'),
       ('Kinh doanh'),
       ('Phát triển sản phẩm'),
       ('Dịch vụ khách hàng'),
       ('Tài chính'),
       ('Nhân sự'),
       ('Hành chính'),
       ('Nghiên cứu và phát triển');

INSERT INTO `position` (name)
VALUES ('DEV'),
       ('TEST'),
       ('SCRUM_MASTER'),
       ('PM');

ALTER TABLE `account` ADD COLUMN gender VARCHAR(50);
INSERT INTO `account` (email, username, fullname, create_date, department_id, position_id, gender)
VALUES ('admin@gmail.com', 'admin', 'System Admin', NOW(), 1, 1, 'M'),
       ('user2@gmail.com', 'user2', 'User 2', NOW(), 2, 2, 'F'),
       ('user3@gmail.com', 'user3', 'User 3', NOW(), 3, 3, 'U'),
       ('user4@gmail.com', 'user4', 'User 4', NOW(), 4, 4, 'M'),
       ('user5@gmail.com', 'user5', 'User 5', NOW(), 1, 1, 'F'),
       ('user6@gmail.com', 'user6', 'User 6', NOW(), 1, 1, 'M'),
       ('user7@gmail.com', 'user7', 'User 7', NOW(), 1, 1, 'F'),
       ('user8@gmail.com', 'user8', 'User 8', NOW(), 1, 1, 'M'),
       ('user9@gmail.com', 'user9', 'User 9', NOW(), 1, 1, 'M'),
       ('user10@gmail.com', 'user10', 'User 10', NOW(), 1, 1, 'M'),
       ('user11@gmail.com', 'user11', 'User 11', NOW(), 1, 1, 'F'),
       ('user12@gmail.com', 'user12', 'User 12', NOW(), 1, 1, 'M'),
       ('user13@gmail.com', 'user13', 'User 13', NOW(), 1, 1, 'M'),
       ('user14@gmail.com', 'user14', 'User 14', NOW(), 1, 1, 'F'),
       ('user15@gmail.com', 'user15', 'User 15', NOW(), 1, 1, 'M'),
       ('user16@gmail.com', 'user16', 'User 16', NOW(), 1, 1, 'M'),
       ('user17@gmail.com', 'user17', 'User 17', NOW(), 1, 1, 'M'),
       ('user18@gmail.com', 'user18', 'User 18', NOW(), 1, 1, 'F'),
       ('user19@gmail.com', 'user19', 'User 19', NOW(), 1, 1, 'M'),
       ('user20@gmail.com', 'user20', 'User 20', NOW(), 1, 1, 'M'),
       ('user21@gmail.com', 'user21', 'User 21', NOW(), 1, 1, 'F'),
       ('user22@gmail.com', 'user22', 'User 22', NOW(), 1, 1, 'M'),
       ('testnull@gmail.com', 'testnull', 'Test Null Dept', NOW(), NULL, 1, 'F');

INSERT INTO `group` (name, create_date, creator_id)
VALUES ('Nhom 1', '2021-01-01', 1),
       ('Nhom 2', '2021-02-01', 2),
       ('Nhom Khong Nguoi', '2021-03-01', 3),
       ('Nhom Dong Nguoi', NOW(), 1);

INSERT INTO group_account (join_date, group_id, account_id)
VALUES ('2021-01-02', 1, 1),
       ('2021-01-02', 1, 2),
       ('2021-01-02', 1, 3),
       ('2021-01-02', 1, 4),
       ('2021-01-02', 1, 5),
       ('2021-01-02', 1, 6),
       ('2021-02-02', 2, 1),
       ('2021-02-02', 2, 7),
       ('2021-02-02', 2, 8),
       (NOW(), 4, 1), (NOW(), 4, 2), (NOW(), 4, 3), (NOW(), 4, 4), (NOW(), 4, 5),
       (NOW(), 4, 6), (NOW(), 4, 7), (NOW(), 4, 8), (NOW(), 4, 9), (NOW(), 4, 10),
       (NOW(), 4, 11), (NOW(), 4, 12), (NOW(), 4, 13), (NOW(), 4, 14), (NOW(), 4, 15),
       (NOW(), 4, 16), (NOW(), 4, 17), (NOW(), 4, 18), (NOW(), 4, 19), (NOW(), 4, 20), (NOW(), 4, 21);;

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
       ('EXAM03', 'De thi C++', 90, '2021-05-03', 3, 3),
       ('EXAM_NEW', 'De thi moi tao', 60, NOW(), 1, 1),
       ('EXAM_SHORT', 'De thi Nhanh', 15, '2021-05-04', 1, 1);

INSERT INTO exam_question (exam_id, question_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (3, 3);