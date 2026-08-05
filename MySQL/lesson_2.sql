# Tạo DB Testing System.
CREATE DATABASE IF NOT EXISTS testing_system;

USE testing_system;

# Table 1:Department
CREATE TABLE IF NOT EXISTS department
(
    id   BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256)
);

INSERT INTO department (name) VALUES ('Sale'),
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