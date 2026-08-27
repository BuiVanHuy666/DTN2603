package lesson_3.exercises;

import java.util.Random;

public class Exercise1 {
    public static void question1() {
//        Khai báo 2 số lương có kiểu dữ liệu là float.
//        Khởi tạo Lương của Account 1 là 5240.5 $
//        Khởi tạo Lương của Account 2 là 10970.055$
//        Khai báo 1 số int để làm tròn Lương của Account 1 và in số int đó ra
//        Khai báo 1 số int để làm tròn Lương của Account 2 và in số int đó ra

        float salaryAccount1 = 5240.5f;
        float salaryAccount2 = 10970.055f;

        int roundedSalary1 = (int) Math.round(salaryAccount1);
        int roundedSalary2 = (int) Math.round(salaryAccount2);

        System.out.println("Lương Account 1 sau khi làm tròn: " + roundedSalary1);
        System.out.println("Lương Account 2 sau khi làm tròn: " + roundedSalary2);
    }

    public static Object question2() {
//        Lấy ngẫu nhiên 1 số có 5 chữ số (những số dưới 5 chữ số thì sẽ thêm có số 0 ở đầu cho đủ 5 chữ số)
        Random random = new Random();

        int randomNumber = random.nextInt(100000);

        String formattedNumber = String.format("%05d", randomNumber);
        System.out.println("Số ngẫu nhiên 5 chữ số: " + formattedNumber);
        return formattedNumber;
    }

    //    Lấy 2 số cuối của số ở Question 2 và in ra.
//    Gợi ý:
//    Cách 1: convert số có 5 chữ số ra String, sau đó lấy 2 số cuối
//    Cách 2: chia lấy dư số đó cho 100
    public static void question3() {
        String formattedNumber = (String) question2();

        // Cách 1: Xử lý qua String
        String lastTwoDigitsStr = formattedNumber.substring(3, 5);
        System.out.println("Cách 1 - Lấy 2 số cuối (Dùng String): " + lastTwoDigitsStr);

        // Cách 2: Chia lấy dư cho 100
        int rawNumber = Integer.parseInt(formattedNumber);
        int lastTwoDigitsMath = rawNumber % 100;
        System.out.println("Cách 2 - Lấy 2 số cuối (Dùng Toán học): " + String.format("%02d", lastTwoDigitsMath));
    }

    //    Viết 1 method nhập vào 2 số nguyên a và b và trả về thương của chúng.
    public static float question4(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Không thể chia cho 0!");
        }

        return (float) a / b;
    }
}
