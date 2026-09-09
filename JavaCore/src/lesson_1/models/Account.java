package lesson_1.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Account {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String fullName;

    @Getter @Setter
    private Department department;

    @Getter @Setter
    private Position position;

    @Getter @Setter
    private Date createdAt;

    public Account(int id, String email, String username, String fullName, Department department, Position position, Date createdAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.department = department;
        this.position = position;
        this.createdAt = createdAt;
    }

    public Account() {}

    @Override
    public String toString() {
        return "Account [Email: " + email + ", Username: " + username +
                ", FullName: " + fullName + ", CreateDate: " + createdAt + "]";
    }

}
