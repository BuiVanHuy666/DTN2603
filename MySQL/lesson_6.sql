-- Question 1: Tạo store để người dùng nhập vào tên phòng ban và in ra tất cả các account thuộc phòng ban đó.
DELIMITER $$
CREATE PROCEDURE GetAccountsByDepartmentName(IN p_dep_name VARCHAR(256))
BEGIN
    SELECT a.*
    FROM `account` a
    JOIN department d ON a.department_id = d.id
    WHERE d.name = p_dep_name;
END $$
DELIMITER ;

CALL GetAccountsByDepartmentName('sale');

-- Question 2: Tạo store để in ra số lượng account trong mỗi group.
DELIMITER $$
CREATE PROCEDURE CountAccountInGroup()
BEGIN
    SELECT g.name, COUNT(ga.account_id) AS account_count
    FROM `group` g
    LEFT JOIN group_account ga ON g.id = ga.group_id
    GROUP BY g.id;
END $$
DELIMITER ;

CALL CountAccountInGroup();

-- Question 3: Tạo store để thống kê mỗi type question có bao nhiêu question được tạo trong tháng hiện tại.
DELIMITER $$
CREATE PROCEDURE CountTypeQuestionThisMonth()
BEGIN
    SELECT tq.name, COUNT(q.id) AS question_count
    FROM type_question tq
    LEFT JOIN question q ON tq.id = q.type_id
    AND MONTH(q.create_date) = MONTH(NOW())
    AND YEAR(q.create_date) = YEAR(NOW())
    GROUP BY tq.id;
END $$
DELIMITER ;

CALL CountTypeQuestionThisMonth();

-- Question 4: Tạo store để trả ra id của type question có nhiều câu hỏi nhất.
DELIMITER $$
CREATE PROCEDURE GetMostPopularTypeQuestionId(OUT v_type_ids VARCHAR(255))
BEGIN
    SELECT GROUP_CONCAT(type_id) INTO v_type_ids
    FROM (
             SELECT type_id
             FROM question
             GROUP BY type_id
             HAVING COUNT(id) = (
                 SELECT MAX(question_count)
                 FROM (
                          SELECT COUNT(id) AS question_count
                          FROM question
                          GROUP BY type_id
                      ) AS max_counts
             )
         ) AS a;
END $$
DELIMITER ;

CALL GetMostPopularTypeQuestionId(@v_type_ids);
SELECT @v_type_ids AS 'Kết quả Test';

-- Question 5: Sử dụng store ở question 4 để tìm ra tên của type question.
DELIMITER $$
CREATE PROCEDURE GetMostPopularTypeQuestionName()
BEGIN
    DECLARE v_ids VARCHAR(255);
    CALL GetMostPopularTypeQuestionId(v_ids);

    SELECT name
    FROM type_question
    WHERE FIND_IN_SET(id, v_ids);
END $$
DELIMITER ;

CALL GetMostPopularTypeQuestionName();

-- Question 6: Viết 1 store cho phép người dùng nhập vào 1 chuỗi và trả về group có tên chứa chuỗi của người dùng nhập vào hoặc trả về user có username chứa chuỗi của người dùng nhập vào.
DELIMITER $$
CREATE PROCEDURE SearchGroupOrUser(IN p_search_string VARCHAR(256))
BEGIN
    SELECT 'Group' AS type, name AS result_name
    FROM `group`
    WHERE name LIKE CONCAT('%', p_search_string, '%')

    UNION ALL

    SELECT 'Account' AS type, username AS result_name
    FROM `account`
    WHERE username LIKE CONCAT('%', p_search_string, '%');
END $$
DELIMITER ;

CALL SearchGroupOrUser('user');

-- Question 7: Viết 1 store cho phép người dùng nhập vào thông tin fullName, email và trong store sẽ tự động gán:
    # username sẽ giống email nhưng bỏ phần @..mail đi
    # positionID: sẽ có default là developer
    # departmentID: sẽ được cho vào 1 phòng chờ Sau đó in ra kết quả tạo thành công
DELIMITER $$
CREATE PROCEDURE AutoCreateAccount(IN p_fullname VARCHAR(256), IN p_email VARCHAR(256))
BEGIN
    DECLARE v_username VARCHAR(256);
    DECLARE v_position_id BIGINT;
    DECLARE v_department_id BIGINT;

    -- Xử lý chuỗi: Lấy phần chữ trước dấu @ làm username
    SET v_username = SUBSTRING_INDEX(p_email, '@', 1);

    -- Lấy ID mặc định
    SELECT id INTO v_position_id FROM position WHERE name = 'DEV' LIMIT 1;
    -- Giả sử trong bảng department đã có dòng 'Phòng chờ'
    SELECT id INTO v_department_id FROM department WHERE name = 'TEMP_DEPT' LIMIT 1;

    INSERT INTO `account` (email, username, fullname, create_date, department_id, position_id)
    VALUES (p_email, v_username, p_fullname, NOW(), v_department_id, v_position_id);

    SELECT 'Tạo Account thành công!' AS message;
END $$
DELIMITER ;

CALL AutoCreateAccount('Nguyen Van A', 'nguyenvana@gmail.com');

-- Question 8: Viết 1 store cho phép người dùng nhập vào Essay hoặc Multiple-Choice để thống kê câu hỏi essay hoặc multiple-choice nào có content dài nhất
DELIMITER $$
CREATE PROCEDURE GetLongestQuestionByType(IN p_type_name ENUM('ESSAY', 'MULTIPLE_CHOICE'))
BEGIN
    SELECT q.*
    FROM question q
             JOIN type_question tq ON q.type_id = tq.id
    WHERE tq.name = p_type_name
      AND LENGTH(q.content) = (
        SELECT MAX(LENGTH(q2.content))
        FROM question q2
        JOIN type_question tq2 ON q2.type_id = tq2.id
        WHERE tq2.name = p_type_name
    );
END $$
DELIMITER ;

CALL GetLongestQuestionByType('ESSAY');
CALL GetLongestQuestionByType('MULTIPLE_CHOICE');

-- Question 9: Viết 1 store cho phép người dùng xóa exam dựa vào ID
DELIMITER $$
CREATE PROCEDURE DeleteExamById(IN p_exam_id BIGINT)
BEGIN
    DELETE FROM exam_question WHERE exam_id = p_exam_id;
    DELETE FROM exam WHERE id = p_exam_id;
END $$
DELIMITER ;

CALL DeleteExamById(1);

-- Question 10: Tìm ra các exam được tạo từ 3 năm trước và xóa các exam đó đi (sử dụng store ở câu 9 để xóa) Sau đó in số lượng record đã remove từ các table liên quan trong khi removing

DELIMITER $$
CREATE PROCEDURE DeleteOldExamsAndReport()
BEGIN
    DECLARE v_exam_id BIGINT;
    DECLARE v_done INT DEFAULT FALSE;
    DECLARE v_deleted_exams INT DEFAULT 0;
    DECLARE v_deleted_exam_questions INT DEFAULT 0;

    DECLARE cur_exam CURSOR FOR
    SELECT id FROM exam WHERE YEAR(create_date) = YEAR(NOW()) - 3;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = TRUE;

    SELECT COUNT(*) INTO v_deleted_exams
    FROM exam
    WHERE YEAR(create_date) = YEAR(NOW()) - 3;

    SELECT COUNT(*) INTO v_deleted_exam_questions
    FROM exam_question eq
    JOIN exam e ON eq.exam_id = e.id
    WHERE YEAR(create_date) = YEAR(NOW()) - 3;

    OPEN cur_exam;
    delete_loop: LOOP
        FETCH cur_exam INTO v_exam_id;
        IF v_done THEN
        LEAVE delete_loop;
        END IF;

        CALL DeleteExamById(v_exam_id);
    END LOOP;
    CLOSE cur_exam;

    SELECT v_deleted_exams AS exams_removed, v_deleted_exam_questions AS exam_questions_removed;
END $$
DELIMITER ;

CALL DeleteOldExamsAndReport();

-- Question 11: Viết store cho phép người dùng xóa phòng ban bằng cách người dùng nhập vào tên phòng ban và các account thuộc phòng ban đó sẽ được chuyển về phòng ban default là phòng ban chờ việc
DELIMITER $$
CREATE PROCEDURE DeleteDepartmentSafe(IN p_dep_name VARCHAR(256))
BEGIN
    DECLARE v_dep_id BIGINT;
    DECLARE v_waiting_room_id BIGINT;

    SELECT id INTO v_dep_id
    FROM department
    WHERE name = p_dep_name;

    SELECT id INTO v_waiting_room_id
    FROM department
    WHERE name = 'TEMP_DEPT';

    UPDATE `account`
    SET department_id = v_waiting_room_id
    WHERE department_id = v_dep_id;

    DELETE FROM department WHERE id = v_dep_id;

    SELECT CONCAT('Đã xóa phòng ban ', p_dep_name, ' và chuyển nhân sự sang phòng chờ') AS message;
END $$
DELIMITER ;

-- Question 12: Viết store để in ra mỗi tháng có bao nhiêu câu hỏi được tạo trong năm nay
DELIMITER $$
CREATE PROCEDURE StatQuestionsPerMonthThisYear()
BEGIN
    WITH RECURSIVE months AS (
        SELECT 1 AS month_num
        UNION ALL
        SELECT month_num + 1 FROM months WHERE month_num < 12
    )
    SELECT
        m.month_num AS `Tháng`,
        COUNT(q.id) AS `Số câu hỏi`
    FROM months m
    LEFT JOIN question q
    ON MONTH(q.create_date) = m.month_num
    AND YEAR(q.create_date) = YEAR(NOW())
    GROUP BY m.month_num;
END $$
DELIMITER ;

-- Question 13: Viết store để in ra mỗi tháng có bao nhiêu câu hỏi được tạo trong 6 tháng gần đây nhất (Nếu tháng nào không có thì sẽ in ra là "không có câu hỏi nào trong tháng")
DELIMITER $$
CREATE PROCEDURE StatQuestionsLast6Months()
BEGIN
    WITH RECURSIVE last_6_months AS (
        SELECT 0 AS n
        UNION ALL
        SELECT n + 1 FROM last_6_months WHERE n < 5
    ),
   month_list AS (
       SELECT DATE_FORMAT(DATE_SUB(NOW(), INTERVAL n MONTH), '%Y-%m') AS month_year
       FROM last_6_months
   )
    SELECT
        ml.month_year AS `Tháng`,
        CASE
            WHEN COUNT(q.id) = 0 THEN 'không có câu hỏi nào trong tháng'
            ELSE CAST(COUNT(q.id) AS CHAR)
            END AS `Số lượng`
    FROM month_list ml
             LEFT JOIN question q ON DATE_FORMAT(q.create_date, '%Y-%m') = ml.month_year
    GROUP BY ml.month_year
    ORDER BY ml.month_year DESC;
END $$
DELIMITER ;
