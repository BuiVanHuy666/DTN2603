package lesson_1;

import lesson_1.models.Department;

public class Main {
    public static void main(String[] args) {
        // Tạo file Program.java có chứa main() method và khởi tạo ít nhất 3 đối tượng đối với mỗi table trong java
        Department department1 = new Department(1, "Sales", "Tầng 1");
        Department department2 = new Department(2, "Marketing", "Tầng 2");
        Department department3 = new Department(3, "IT", "Tầng 3");

        //Trong file Program.java, hãy in ít nhất 1 giá trị của mỗi đối tượng ra.
        System.out.println("Department 1: " + department1.getName());
        System.out.println("Department 2: " + department2.getName());
        System.out.println("Department 3: " + department3.getName());
    }
}