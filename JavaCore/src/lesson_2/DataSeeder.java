package lesson_2;

import lesson_1.enums.PositionName;
import lesson_1.models.*;

import java.util.Date;

public class DataSeeder {

    public static Department[] seedDepartments() {
        return new Department[] {
                new Department(1, "Sales", "Tầng 1"),
                new Department(2, "Marketing", "Tầng 2"),
                new Department(3, "IT", "Tầng 3"),
                new Department(4, "HR", "Tầng 4"),
                new Department(5, "Finance", "Tầng 5")
        };
    }

    public static Position[] seedPositions() {
        return new Position[] {
                new Position(1, PositionName.DEV),
                new Position(2, PositionName.TEST),
                new Position(3, PositionName.SCRUM_MASTER),
                new Position(4, PositionName.PM)
        };
    }

    public static Account[] seedAccounts(Department[] departments, Position[] positions) {
        return new Account[] {
                new Account(1, "NguyenVanA@gmail.com", "nguyenvana", "Nguyễn Văn A", departments[0], positions[0], new Date()),
                new Account(2, "NguyenVanB@gmail.com", "nguyenvanb", "Nguyễn Văn B", null, positions[1], new Date()),
                new Account(3, "NguyenVanC@gmail.com", "nguyenvanc", "Nguyễn Văn C", departments[2], positions[3], new Date())
        };
    }

    public static Group[] seedGroups(Account[] accounts) {
        return new Group[] {
                new Group(1, "Java Fresher", accounts[0], new Date()),
                new Group(2, "C# Fresher", accounts[1], new Date())
        };
    }

    public static GroupAccount[] seedGroupAccounts(Group[] groups, Account[] accounts) {
        return new GroupAccount[] {
                new GroupAccount(groups[0], accounts[0], new Date()),
                new GroupAccount(groups[0], accounts[1], new Date()),
                new GroupAccount(groups[1], accounts[1], new Date())
        };
    }
}