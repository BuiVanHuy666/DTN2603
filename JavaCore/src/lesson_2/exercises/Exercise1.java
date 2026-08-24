package lesson_2.exercises;

import lesson_1.enums.PositionName;
import lesson_1.models.*;
import lesson_2.DataSeeder;

public class Exercise1 {
    private static final Department[] departments = DataSeeder.seedDepartments();
    private static final Position[] positions = DataSeeder.seedPositions();
    private static final Account[] accounts = DataSeeder.seedAccounts(departments, positions);
    private static final Group[] groups = DataSeeder.seedGroups(accounts);
    private static final GroupAccount[] groupAccounts = DataSeeder.seedGroupAccounts(groups, accounts);

    public static void question1() {
        // Kiểm tra account thứ 2
        // Nếu không có phòng ban (tức là department == null) thì sẽ in ra text "Nhân viên này chưa có phòng ban"
        // Nếu không thì sẽ in ra text "Phòng ban của nhân viên này là …"

        Account account2 = accounts[1];
        if (account2.getDepartment() == null) {
            System.out.println("Nhân viên này chưa có phòng ban");
        } else {
            System.out.println("Phòng ban của nhân viên này là " + account2.getDepartment().getName());
        }
    }

    public static void question2() {
        // Kiểm tra account thứ 2
        // Nếu không có group thì sẽ in ra text "Nhân viên này chưa có group"
        // Nếu có mặt trong 1 hoặc 2 group thì sẽ in ra text "Group của nhân viên này là Java Fresher, C# Fresher"
        // Nếu có mặt trong 3 Group thì sẽ in ra text "Nhân viên này là người quan trọng, tham gia nhiều group"
        // Nếu có mặt trong 4 group trở lên thì sẽ in ra text "Nhân viên này là người hóng chuyện, tham gia tất cả các group"

        Account account2 = accounts[1];

        int groupCount = countGroupsByAccountId(account2.getId());

        if (groupCount == 0) {
            System.out.println("Nhân viên này chưa có group");
        } else if (groupCount == 1 || groupCount == 2) {
            System.out.println("Group của nhân viên này là Java Fresher, C# Fresher");
        } else if (groupCount == 3) {
            System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
        } else {
            System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
        }
    }

    public static void question3() {
        // Sử dụng toán tử ternary để làm Question 1
        Account account2 = accounts[1];
        String result = (account2.getDepartment() == null)
                ? "Nhân viên này chưa có phòng ban"
                : "Phòng ban của nhân viên này là " + account2.getDepartment().getName();
        System.out.println(result);
    }

    public static void question4() {
        // Sử dụng toán tử ternary để làm yêu cầu sau:
        // Kiểm tra Position của account thứ 1
        // Nếu Position = Dev thì in ra text "Đây là Developer"
        // Nếu không phải thì in ra text "Người này không phải là Developer"
        Account account1 = accounts[0];
        String result = (account1.getPosition().getName() == PositionName.DEV)
                ? "Đây là Developer"
                : "Người này không phải là Developer";
        System.out.println(result);
    }

    public static void question5() {
        // Lấy ra số lượng account trong nhóm thứ 1 và in ra theo format sau:
        // Nếu số lượng account = 1 thì in ra "Nhóm có một thành viên"
        // Nếu số lượng account = 2 thì in ra "Nhóm có hai thành viên"
        // Nếu số lượng account = 3 thì in ra "Nhóm có ba thành viên"
        // Còn lại in ra "Nhóm có nhiều thành viên"
        Group group1 = groups[0];
        int accountCount = 0;

        for (GroupAccount ga : groupAccounts) {
            if (ga.getGroup().getId() == group1.getId()) {
                accountCount++;
            }
        }

        switch (accountCount) {
            case 1:
                System.out.println("Nhóm có một thành viên");
                break;
            case 2:
                System.out.println("Nhóm có hai thành viên");
                break;
            case 3:
                System.out.println("Nhóm có ba thành viên");
                break;
            default:
                System.out.println("Nhóm có nhiều thành viên");
                break;
        }
    }

    public static void question6() {
        // Sử dụng switch case để làm lại Question 2
        Account account2 = accounts[1];
        // Tiếp tục sử dụng Helper Method ở đây
        int groupCount = countGroupsByAccountId(account2.getId());

        switch (groupCount) {
            case 0:
                System.out.println("Nhân viên này chưa có group");
                break;
            case 1:
            case 2:
                System.out.println("Group của nhân viên này là Java Fresher, C# Fresher");
                break;
            case 3:
                System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
                break;
            default:
                System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
                break;
        }
    }

    public static void question7() {
        // Sử dụng switch case để làm lại Question 4
        Account account1 = accounts[0];
        switch (account1.getPosition().getName()) {
            case DEV:
                System.out.println("Đây là Developer");
                break;
            default:
                System.out.println("Người này không phải là Developer");
                break;
        }
    }

    public static void question8() {
        // In ra thông tin các account bao gồm: Email, FullName và tên phòng ban của họ
        for (Account account : accounts) {
            String departmentName = (account.getDepartment() != null)
                    ? account.getDepartment().getName()
                    : "Chưa có phòng ban";
            System.out.println("Email: " + account.getEmail() + " | FullName: " + account.getFullName() + " | Department: " + departmentName);
        }
    }

    public static void question9() {
        // In ra thông tin các phòng ban bao gồm: id và name
        for (Department department : departments) {
            System.out.println("ID: " + department.getId() + " | Name: " + department.getName());
        }
    }

    public static void question10() {
        // In ra thông tin các account bao gồm: Email, FullName và tên phòng ban của
        // họ theo định dạng như sau:
        // Thông tin account thứ 1 là:
        // Email: NguyenVanA@gmail.com
        // Full name: Nguyễn Văn A
        // Phòng ban: Sale
        for (int i = 0; i < accounts.length; i++) {
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].getEmail());
            System.out.println("Full name: " + accounts[i].getFullName());
            String depName = (accounts[i].getDepartment() != null)
                    ? accounts[i].getDepartment().getName()
                    : "Chưa có phòng ban";
            System.out.println("Phòng ban: " + depName);
        }
    }

    public static void question11() {
        // In ra thông tin các phòng ban bao gồm: id và name theo định dạng sau:
        // Thông tin department thứ 1 là:
        // Id: 1
        // Name: Sale
        for (int i = 0; i < departments.length; i++) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].getId());
            System.out.println("Name: " + departments[i].getName());
        }
    }

    public static void question12() {
        // Chỉ in ra thông tin 2 department đầu tiên theo định dạng như Question 10
        for (int i = 0; i < 2 && i < departments.length; i++) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("Id: " + departments[i].getId());
            System.out.println("Name: " + departments[i].getName());
        }
    }

    public static void question13() {
        // In ra thông tin tất cả các account ngoại trừ account thứ 2
        for (int i = 0; i < accounts.length; i++) {
            if (i == 1) continue;
            System.out.println("Account ID: " + accounts[i].getId() + " - Name: " + accounts[i].getFullName());
        }
    }

    public static void question14() {
        // In ra thông tin tất cả các account có id < 4
        for (Account account : accounts) {
            if (account.getId() >= 4) continue;
            System.out.println("Account ID: " + account.getId() + " - Name: " + account.getFullName());
        }
    }

    public static void question15() {
        // In ra các số chẵn nhỏ hơn hoặc bằng 20
        for (int i = 0; i <= 20; i++) {
            if (i % 2 != 0) continue;
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void question16() {
        // Làm lại các Question ở phần FOR bằng cách sử dụng WHILE kết hợp với lệnh break, continue
        System.out.println("--- In account ngoại trừ account thứ 2 (dùng WHILE) ---");
        int i = 0;
        while (i < accounts.length) {
            if (i == 1) {
                i++;
                continue;
            }
            System.out.println("Account ID: " + accounts[i].getId() + " - Name: " + accounts[i].getFullName());
            i++;
        }
    }

    public static void question17() {
        // Làm lại các Question ở phần FOR bằng cách sử dụng DO-WHILE kết hợp với lệnh break, continue
        System.out.println("--- In số chẵn nhỏ hơn hoặc bằng 20 (dùng DO-WHILE) ---");
        int j = 0;
        do {
            if (j % 2 != 0) {
                j++;
                continue;
            }
            System.out.print(j + " ");
            j++;
        } while (j <= 20);
        System.out.println();
    }

    private static int countGroupsByAccountId(int accountId) {
        int count = 0;
        for (GroupAccount ga : groupAccounts) {
            if (ga.getAccount().getId() == accountId) {
                count++;
            }
        }
        return count;
    }
}
