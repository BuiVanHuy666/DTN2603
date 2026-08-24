package lesson_2.exercises;

import lesson_1.models.Exam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Exercise3 {
    public static void question1() {
        // In ra thông tin Exam thứ 1 và property create date sẽ được format theo định dạng Vietnamese
        Exam exam1 = new Exam();
        exam1.setCreatedAt(new Date());

        Locale localeVN = Locale.of("vi", "VN");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, localeVN);
        System.out.println("Ngày tạo (Vietnamese): " + dateFormat.format(exam1.getCreatedAt()));
    }

    public static void question2() {
        // In ra thông tin: Exam đã tạo ngày nào theo định dạng
        // Năm – tháng – ngày – giờ – phút – giây
        Exam exam1 = new Exam();
        exam1.setCreatedAt(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        System.out.println("Ngày tạo: " + sdf.format(exam1.getCreatedAt()));
    }

    public static void question3() {
        // Chỉ in ra năm của create date property trong Question 2
        Exam exam1 = new Exam();
        exam1.setCreatedAt(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        System.out.println("Năm tạo: " + sdf.format(exam1.getCreatedAt()));
    }

    public static void question4() {
        // Chỉ in ra tháng và năm của create date property trong Question 2
        Exam exam1 = new Exam();
        exam1.setCreatedAt(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        System.out.println("Tháng và Năm tạo: " + sdf.format(exam1.getCreatedAt()));
    }

    public static void question5() {
        // Chỉ in ra "MM-DD" của create date trong Question 2
        Exam exam1 = new Exam();
        exam1.setCreatedAt(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        System.out.println("MM-DD tạo: " + sdf.format(exam1.getCreatedAt()));
    }
}