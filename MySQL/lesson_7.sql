-- Question 1: Tạo trigger không cho phép người dùng nhập vào Group có ngày tạo trước 1 năm trước
DELIMITER $$
CREATE TRIGGER trg_check_group_create_date
    BEFORE INSERT ON `group`
    FOR EACH ROW
BEGIN
    IF NEW.create_date < DATE_SUB(NOW(), INTERVAL 1 YEAR) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Cannot create group with create_date older than 1 year';
    END IF;
END $$
DELIMITER ;
INSERT INTO `group` (name, create_date, creator_id)
VALUES ('Nhóm Hợp Lệ', NOW(), 1);
INSERT INTO `group` (name, create_date, creator_id)
VALUES ('Nhóm Không hợp lệ', '2020-01-01', 1);

-- Question 2: Tạo trigger Không cho phép người dùng thêm bất kỳ user nào vào department "Sale" nữa, khi thêm thì hiện ra thông báo "Department "Sale" cannot add more user"
DELIMITER $$
CREATE TRIGGER trg_prevent_add_user_to_sale
    BEFORE INSERT ON `account`
    FOR EACH ROW
BEGIN
    DECLARE v_sale_dept_id BIGINT;

    SELECT id INTO v_sale_dept_id
    FROM department
    WHERE name = 'Sale'
    LIMIT 1;

    IF NEW.department_id = v_sale_dept_id THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Department "Sale" cannot add more user';
    END IF;
END $$
DELIMITER ;

INSERT INTO `account` (email, username, fullname, create_date, department_id, position_id, gender)
VALUES ('test.it@gmail.com', 'testit', 'User Test IT', NOW(), 4, 1, 'M');
INSERT INTO `account` (email, username, fullname, create_date, department_id, position_id, gender)
VALUES ('test.sale@gmail.com', 'testsale', 'User Test Sale', NOW(), 1, 1, 'F');

-- Question 3: Cấu hình 1 group có nhiều nhất là 5 user
DELIMITER $$
CREATE TRIGGER trg_max_5_users_per_group
    BEFORE INSERT ON group_account
    FOR EACH ROW
BEGIN
    DECLARE v_count INT;

    SELECT COUNT(*) INTO v_count
    FROM group_account
    WHERE group_id = NEW.group_id;

    IF v_count >= 5 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Cannot add more than 5 users to a group';
    END IF;
END $$
DELIMITER ;
-- Question 4: Cấu hình 1 bài thi có nhiều nhất là 10 Question
DELIMITER $$
CREATE TRIGGER trg_max_10_questions_per_exam
    BEFORE INSERT ON exam_question
    FOR EACH ROW
BEGIN
    DECLARE v_count INT;

    SELECT COUNT(*) INTO v_count
    FROM exam_question
    WHERE exam_id = NEW.exam_id;

    IF v_count >= 10 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: An exam can have at most 10 questions!';
    END IF;
END $$
DELIMITER ;

-- Question 5: Tạo trigger không cho phép người dùng xóa tài khoản có email là admin@gmail.com (đây là tài khoản admin, không cho phép user xóa), còn lại các tài khoản khác thì sẽ cho phép xóa và sẽ xóa tất cả các thông tin liên quan tới user đó
DELIMITER $$
CREATE TRIGGER trg_prevent_delete_admin
    BEFORE DELETE ON `account`
    FOR EACH ROW
BEGIN
    IF OLD.email = 'admin@gmail.com' THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Cannot delete admin account';
    ELSE
        DELETE FROM group_account WHERE account_id = OLD.id;

        DELETE FROM exam_question WHERE exam_id IN (SELECT id FROM exam WHERE creator_id = OLD.id);
        DELETE FROM exam WHERE creator_id = OLD.id;

        DELETE FROM exam_question WHERE question_id IN (SELECT id FROM question WHERE creator_id = OLD.id);
        DELETE FROM answer WHERE question_id IN (SELECT id FROM question WHERE creator_id = OLD.id);
        DELETE FROM question WHERE creator_id = OLD.id;

        DELETE FROM group_account WHERE group_id IN (SELECT id FROM `group` WHERE creator_id = OLD.id);
        DELETE FROM `group` WHERE creator_id = OLD.id;

    END IF;
END $$
DELIMITER ;
-- Question 6: Không sử dụng cấu hình default cho field DepartmentID của table Account, hãy tạo trigger cho phép người dùng khi tạo account không điền vào departmentID thì sẽ được phân vào phòng ban "waiting Department"
DELIMITER $$
CREATE TRIGGER trg_set_waiting_department
    BEFORE INSERT ON `account`
    FOR EACH ROW
BEGIN
    DECLARE v_waiting_dept_id BIGINT;

    IF NEW.department_id IS NULL THEN
        SELECT id INTO v_waiting_dept_id
        FROM department
        WHERE name = 'waiting Department'
        LIMIT 1;

        IF v_waiting_dept_id IS NULL THEN
            INSERT INTO department (name) VALUES ('waiting Department');
            SET v_waiting_dept_id = LAST_INSERT_ID();
        END IF;

        SET NEW.department_id = v_waiting_dept_id;
    END IF;
END $$
DELIMITER ;

-- Question 7: Cấu hình 1 bài thi chỉ cho phép user tạo tối đa 4 answers cho mỗi question, trong đó có tối đa 2 đáp án đúng.
DROP TRIGGER IF EXISTS trg_limit_answers;

DELIMITER $$
CREATE TRIGGER trg_limit_answers
    BEFORE INSERT ON answer
    FOR EACH ROW
BEGIN
    DECLARE v_total_answers INT DEFAULT 0;
    DECLARE v_correct_answers INT DEFAULT 0;

    SELECT
        COUNT(*),
        IFNULL(SUM(IF(is_correct = TRUE, 1, 0)), 0)
    INTO
        v_total_answers,
        v_correct_answers
    FROM answer
    WHERE question_id = NEW.question_id;

    IF v_total_answers >= 4 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Each question can have at most 4 answers!';
    END IF;

    IF NEW.is_correct = TRUE AND v_correct_answers >= 2 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Each question can have at most 2 correct answers!';
    END IF;

END $$
DELIMITER ;
-- Question 8: Viết trigger sửa lại dữ liệu cho đúng:
-- Nếu người dùng nhập vào gender của account là nam, nữ, chưa xác định
-- Thì sẽ đổi lại thành M, F, U cho giống với cấu hình ở database
DELIMITER $$
CREATE TRIGGER trg_format_gender
    BEFORE INSERT ON `account`
    FOR EACH ROW
BEGIN
    IF NEW.gender = 'nam' THEN
        SET NEW.gender = 'M';

    ELSEIF NEW.gender = 'nữ' THEN
        SET NEW.gender = 'F';

    ELSEIF NEW.gender = 'chưa xác định' THEN
        SET NEW.gender = 'U';

    END IF;
END $$
DELIMITER ;

-- Question 9: Viết trigger không cho phép người dùng xóa bài thi mới tạo được 2 ngày
DELIMITER $$
CREATE TRIGGER trg_prevent_delete_recent_exam
    BEFORE DELETE ON exam
    FOR EACH ROW
BEGIN
    IF OLD.create_date >= DATE_SUB(NOW(), INTERVAL 2 DAY) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Cannot delete an exam that was created within the last 2 days';
    END IF;
END $$
DELIMITER ;

-- Question 10: Viết trigger chỉ cho phép người dùng chỉ được update, delete các question khi question đó chưa nằm trong exam nào
DELIMITER $$
CREATE TRIGGER trg_prevent_update_question
    BEFORE UPDATE ON question
    FOR EACH ROW
BEGIN
    DECLARE v_count INT;

    SELECT COUNT(*) INTO v_count
    FROM exam_question
    WHERE question_id = OLD.id;

    IF v_count > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Cannot UPDATE question because it is already part of an exam';
    END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER trg_prevent_delete_question
    BEFORE DELETE ON question
    FOR EACH ROW
BEGIN
    DECLARE v_count INT;

    SELECT COUNT(*) INTO v_count
    FROM exam_question
    WHERE question_id = OLD.id;

    IF v_count > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error: Cannot DELETE question because it is already part of an exam';
    END IF;
END $$
DELIMITER ;

-- Question 12: Lấy ra thông tin exam trong đó:
--     Duration <= 30 thì sẽ đổi thành giá trị "Short time"
--     30 < Duration <= 60 thì sẽ đổi thành giá trị "Medium time"
--     Duration > 60 thì sẽ đổi thành giá trị "Long time"
SELECT id, code, title, duration, create_date,
    CASE
        WHEN duration <= 30 THEN 'Short time'
        WHEN duration > 30 AND duration <= 60 THEN 'Medium time'
        WHEN duration > 60 THEN 'Long time'
    END AS duration_type
FROM exam;

-- Question 13: Thống kê số account trong mỗi group và in ra thêm 1 column nữa có tên là the_number_user_amount và mang giá trị được quy định như sau:
--     Nếu số lượng user trong group =< 5 thì sẽ có giá trị là few
--     Nếu số lượng user trong group <= 20 và > 5 thì sẽ có giá trị là normal
--     Nếu số lượng user trong group > 20 thì sẽ có giá trị là higher
--     Question 14: Thống kê số mỗi phòng ban có bao nhiêu user, nếu phòng ban nào
--     không có user thì sẽ thay đổi giá trị 0 thành "Không có User"
SELECT g.name AS group_name, COUNT(ga.account_id) AS total_users,
    CASE
        WHEN COUNT(ga.account_id) <= 5 THEN 'few'
        WHEN COUNT(ga.account_id) > 5 AND COUNT(ga.account_id) <= 20 THEN 'normal'
        WHEN COUNT(ga.account_id) > 20 THEN 'higher'
    END AS the_number_user_amount
FROM `group` g
LEFT JOIN group_account ga ON g.id = ga.group_id
GROUP BY g.id, g.name;

