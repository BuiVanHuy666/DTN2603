package lesson_2.exercises;

import lesson_1.enums.PositionName;
import lesson_1.models.*;
import lesson_2.DataSeeder;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Exercise5 {
    private static final Department[] departments = DataSeeder.seedDepartments();
    private static final Position[] positions = DataSeeder.seedPositions();
    private static final Account[] accounts = DataSeeder.seedAccounts(departments, positions);
    private static final Group[] groups = DataSeeder.seedGroups(accounts);

    public static void question1() {
//        Viết lệnh cho phép người dùng nhập 3 số nguyên vào chương trình.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập vào 3 số nguyên:");
        System.out.print("Số thứ 1: ");
        int a = scanner.nextInt();
        System.out.print("Số thứ 2: ");
        int b = scanner.nextInt();
        System.out.print("Số thứ 3: ");
        int c = scanner.nextInt();
        System.out.println("Bạn vừa nhập: " + a + ", " + b + ", " + c);
    }

    public static void question2() {
//        Viết lệnh cho phép người dùng nhập 2 số thực vào chương trình.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập vào 2 số thực:");
        System.out.print("Số thứ 1: ");
        float a = scanner.nextFloat();
        System.out.print("Số thứ 2: ");
        float b = scanner.nextFloat();
        System.out.println("Bạn vừa nhập: " + a + ", " + b);
    }

    public static void question3() {
//        Viết lệnh cho phép người dùng nhập họ và tên.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập họ và tên: ");
        String fullName = scanner.nextLine();
        System.out.println("Tên bạn là: " + fullName);
    }

    public static void question4() {
//        Viết lệnh cho phép người dùng nhập vào ngày sinh nhật của họ.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập ngày sinh (dd-MM-yyyy): ");
        String dob = scanner.nextLine();
        System.out.println("Ngày sinh của bạn: " + dob);
    }

    public static void question5() {
//        Viết lệnh cho phép người dùng tạo account (viết thành method)
//        Đối với property Position, Người dùng nhập vào 1 2 3 4 5 và vào chương trình sẽ chuyển thành Position.Dev...
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Tạo Account mới ---");

        System.out.print("Nhập ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập Email: ");
        String email = scanner.nextLine();

        System.out.print("Nhập Username: ");
        String username = scanner.nextLine();

        System.out.print("Nhập FullName: ");
        String fullName = scanner.nextLine();

        System.out.print("Nhập Position (1.Dev, 2.Test, 3.ScrumMaster, 4.PM): ");
        int posInput = scanner.nextInt();

        PositionName posName = null;
        switch (posInput) {
            case 1:
                posName = PositionName.DEV;
                break;
            case 2:
                posName = PositionName.TEST;
                break;
            case 3:
                posName = PositionName.SCRUM_MASTER;
                break;
            case 4:
                posName = PositionName.PM;
                break;
            default:
                System.out.println("Nhập sai Position! Hủy quá trình tạo tài khoản.");
                return;
        }

        Position position = new Position(posInput, posName);

        Account newAccount = new Account(id, email, username, fullName, null, position, new Date());

        System.out.println("\n🎉 Tạo Account thành công!");
        System.out.println("Thông tin Account vừa tạo:");
        System.out.println("- ID: " + newAccount.getId());
        System.out.println("- Email: " + newAccount.getEmail());
        System.out.println("- Username: " + newAccount.getUsername());
        System.out.println("- FullName: " + newAccount.getFullName());
        System.out.println("- Position: " + newAccount.getPosition().getName());
        System.out.println("- Ngày tạo: " + newAccount.getCreatedAt());
    }
    public static void question6() {
//        Viết lệnh cho phép người dùng tạo department (viết thành method)
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tạo Department mới.");
        System.out.print("Nhập tên phòng ban: ");
        String depName = scanner.nextLine();
        Department dep = new Department(0, depName);
        System.out.println("Đã tạo Department: " + dep.getName());
    }

    public static void question7() {
//        Nhập số chẵn từ console
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập vào 1 số chẵn: ");
        int number = scanner.nextInt();
        if (number % 2 == 0) {
            System.out.println("Số bạn vừa nhập hợp lệ: " + number);
        } else {
            System.out.println("Đây không phải số chẵn!");
        }
    }

    public static void question8() {
//        Viết chương trình thực hiện theo flow sau:
//        Bước 1: Chương trình in ra text "mời bạn nhập vào chức năng muốn sử dụng"
//        Bước 2: Nếu người dùng nhập vào 1 thì sẽ thực hiện tạo account
//        Nếu người dùng nhập vào 2 thì sẽ thực hiện chức năng tạo department
//        Nếu người dùng nhập vào số khác thì in ra text "Mời bạn nhập lại" và quay trở lại bước 1

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Mời bạn nhập vào chức năng muốn sử dụng (1: Tạo Account, 2: Tạo Department): ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                question5();
                break;
            } else if (choice == 2) {
                question6();
                break;
            } else {
                System.out.println("Mời bạn nhập lại.");
            }
        }
    }

    public static void question9() {
//        Viết method cho phép người dùng thêm group vào account theo flow sau:
//        Bước 1: In ra tên các usernames của user cho người dùng xem
//        Bước 2: Yêu cầu người dùng nhập vào username của account
//        Bước 3: In ra tên các group cho người dùng xem
//        Bước 4: Yêu cầu người dùng nhập vào tên của group
//        Bước 5: Dựa vào username và tên của group người dùng vừa chọn, hãy thêm account vào group đó .
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Chức năng thêm Group vào Account ---");

        System.out.println("Danh sách các username hiện có:");
        for (Account acc : accounts) {
            System.out.println("- " + acc.getUsername());
        }

        System.out.print("Mời bạn nhập vào username cần thêm: ");
        String inputUsername = scanner.nextLine();

        Account selectedAccount = findAccountByUsername(inputUsername);

        if (selectedAccount == null) {
            System.out.println("Lỗi: Không tìm thấy tài khoản có username là '" + inputUsername + "'");
            return;
        }

        System.out.println("\nDanh sách các group hiện có:");
        for (Group group : groups) {
            System.out.println("- " + group.getName());
        }

        System.out.print("Mời bạn nhập vào tên group: ");
        String inputGroupName = scanner.nextLine();

        Group selectedGroup = null;
        for (Group group : groups) {
            if (group.getName().equals(inputGroupName)) {
                selectedGroup = group;
                break;
            }
        }

        if (selectedGroup == null) {
            System.out.println("Lỗi: Không tìm thấy group có tên là '" + inputGroupName + "'");
            return;
        }

        GroupAccount newGroupAccount = new GroupAccount(selectedGroup, selectedAccount, new Date());

        System.out.println("\nThành công! Đã thêm account [" + selectedAccount.getUsername() +
                "] vào group [" + selectedGroup.getName() + "].");
    }

    public static void question10() {
//        Bổ sung thêm vào bước 2 của Question 8 như sau:
//        Nếu người dùng nhập vào 3 thì sẽ thực hiện chức năng thêm group vào account
//        Bổ sung thêm Bước 3 của Question 8 như sau: Sau khi người dùng thực hiện xong chức năng ở bước 2 thì in ra dòng text để hỏi   người dùng "Bạn có muốn thực hiện chức năng khác không?". Nếu người dùng chọn "Có" thì quay lại bước 1, nếu người dùng chọn "Không" thì kết thúc chương trình (sử dụng lệnh return để kết thúc chương trình).
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- MENU CHỨC NĂNG ---");
            System.out.println("1: Tạo Account");
            System.out.println("2: Tạo Department");
            System.out.println("3: Thêm Group vào Account");
            System.out.println("4: Thêm Account vào Group ngẫu nhiên");
            System.out.print("Mời bạn chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                question5();
            } else if (choice == 2) {
                question6();
            } else if (choice == 3) {
                question9();
            } else if (choice == 4) {
                question11();
            } else {
                System.out.println("Nhập sai chức năng!");
            }

            System.out.print("\nBạn có muốn thực hiện chức năng khác không? (Có / Không): ");
            String continueChoice = scanner.nextLine();

            if (continueChoice.equalsIgnoreCase("Không")) {
                System.out.println("Kết thúc chương trình.");
                return;
            }
        }
    }

    public static void question11() {
//        Bổ sung thêm vào bước 2 của Question 8 như sau:
//        Nếu người dùng nhập vào 4 thì sẽ thực hiện chức năng thêm account vào 1 nhóm ngẫu nhiên, chức năng sẽ được cài đặt như sau:
//        Bước 1: In ra tên các usernames của user cho người dùng xem
//        Bước 2: Yêu cầu người dùng nhập vào username của account
//        Bước 3: Sau đó chương trình sẽ chọn ngẫu nhiên 1 group
//        Bước 4: Thêm account vào group chương trình vừa chọn ngẫu nhiên
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Chức năng thêm Account vào Group ngẫu nhiên ---");

        System.out.println("Danh sách các username hiện có:");
        for (Account acc : accounts) {
            System.out.println("- " + acc.getUsername());
        }

        System.out.print("Nhập Username của bạn: ");
        String inputUsername = scanner.nextLine();

        Account selectedAccount = findAccountByUsername(inputUsername);

        if (selectedAccount == null) {
            System.out.println("Lỗi: Không tìm thấy tài khoản có username là '" + inputUsername + "'");
            return;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(groups.length);
        Group randomGroup = groups[randomIndex];

        GroupAccount newGroupAccount = new GroupAccount(randomGroup, selectedAccount, new Date());

        System.out.println("Thành công! Đã thêm account [" + selectedAccount.getUsername() +
                "] vào group ngẫu nhiên: [" + randomGroup.getName() + "]");
    }

    private static Account findAccountByUsername(String username) {
        for (Account acc : accounts) {
            if (acc.getUsername().equals(username)) {
                return acc;
            }
        }
        return null;
    }
}
