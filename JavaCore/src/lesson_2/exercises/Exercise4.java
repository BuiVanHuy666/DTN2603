package lesson_2.exercises;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Exercise4 {
    public static void question1() {
//        In ngẫu nhiên ra 1 số nguyên
        Random random = new Random();
        System.out.println("Số nguyên ngẫu nhiên: " + random.nextInt());
    }

    public static void question2() {
//        In ngẫu nhiên ra 1 số thực
        Random random = new Random();
        System.out.println("Số thực ngẫu nhiên: " + random.nextFloat());
    }

    public static void question3() {
//        Khai báo 1 array bao gồm các tên của các bạn trong lớp, sau đó in ngẫu nhiên ra tên của 1 bạn
        String[] students = {"An", "Bình", "Cường", "Dung", "Hoa"};
        Random random = new Random();
        int randomIndex = random.nextInt(students.length);
        System.out.println("Tên ngẫu nhiên: " + students[randomIndex]);
    }

    public static void question4() {
//        Lấy ngẫu nhiên 1 ngày trong khoảng thời gian 24-07-1995 tới ngày 20-12- 1995
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date startDate = sdf.parse("24-07-1995");
            Date endDate = sdf.parse("20-12-1995");

            long startMillis = startDate.getTime();
            long endMillis = endDate.getTime();

            Random random = new Random();
            long randomMillis = startMillis + (long) (random.nextDouble() * (endMillis - startMillis));

            Date randomDate = new Date(randomMillis);
            System.out.println("Ngày ngẫu nhiên Q4: " + sdf.format(randomDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void question5() {
//        Lấy ngẫu nhiên 1 ngày trong khoảng thời gian 1 năm trở lại đây
        long nowMillis = System.currentTimeMillis();
        long oneYearMillis = 365L * 24 * 60 * 60 * 1000;
        long pastYearMillis = nowMillis - oneYearMillis;

        Random random = new Random();
        long randomMillis = pastYearMillis + (long) (random.nextDouble() * (nowMillis - pastYearMillis));

        Date randomDate = new Date(randomMillis);
        System.out.println("Ngày ngẫu nhiên (1 năm trở lại): " + new SimpleDateFormat("dd-MM-yyyy").format(randomDate));
    }

    public static void question6() {
//        Lấy ngẫu nhiên 1 ngày trong quá khứ.
        long nowMillis = System.currentTimeMillis();
        Random random = new Random();
        // Lấy random trong khoảng 10 năm đổ lại
        long maxPastMillis = 10L * 365 * 24 * 60 * 60 * 1000;
        long randomPastMillis = nowMillis - (long) (random.nextDouble() * maxPastMillis);

        Date randomDate = new Date(randomPastMillis);
        System.out.println("Ngày ngẫu nhiên trong quá khứ: " + new SimpleDateFormat("dd-MM-yyyy").format(randomDate));
    }

    public static void question7() {
//        Lấy ngẫu nhiên 1 số có 3 chữ số.
        Random random = new Random();
        int random3Digits = random.nextInt(900) + 100; // Khoảng từ 100 đến 999
        System.out.println("Số có 3 chữ số ngẫu nhiên: " + random3Digits);
    }
}
