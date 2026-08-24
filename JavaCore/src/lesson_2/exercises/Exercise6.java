package lesson_2.exercises;

public class Exercise6 {
    public static void question1() {
//        Tạo method để in ra các số chẵn nguyên dương nhỏ hơn 10
        System.out.print("Các số chẵn nguyên dương nhỏ hơn 10 là: ");
        for (int i = 2; i < 10; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void question2() {
//        Tạo method để in thông tin các account
        System.out.println("In thông tin Account:");
        Exercise1.question8();
    }

    public static void question3() {
//        Tạo method để in ra các số nguyên dương nhỏ hơn 10
        System.out.print("Các số nguyên dương nhỏ hơn 10 là: ");
        for (int i = 1; i < 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
