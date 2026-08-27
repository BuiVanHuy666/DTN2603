package lesson_3.exercises;

public class Exercise3 {
//    Khởi tạo lương có datatype là Integer có giá trị bằng 5000.
//    Sau đó convert lương ra float và hiển thị lương lên màn hình (với số float có 2 số sau dấu thập phân).
    public static void question1() {
        Integer salary = 5000;

        float salaryFloat = salary.floatValue();

        System.out.println("Lương dạng float (2 số thập phân): " + String.format("%.2f", salaryFloat));
    }

//    Khai báo 1 String có value = "1234567"
//    Hãy convert String đó ra số int
    public static void question2() {
        String strNumber = "1234567";

        int resultInt = Integer.parseInt(strNumber);

        System.out.println("Giá trị sau khi convert từ String sang int: " + resultInt);
    }

//    Khởi tạo 1 số Integer có value là chữ "1234567"
//    Sau đó convert số trên thành datatype int
    public static void question3() {
        Integer salaryInteger = Integer.valueOf("1234567");
        int salaryPrimitive = salaryInteger;
        System.out.println("Giá trị sau khi convert từ Integer sang int: " + salaryPrimitive);
    }
}