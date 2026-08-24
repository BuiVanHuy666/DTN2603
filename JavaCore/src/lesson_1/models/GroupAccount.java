package lesson_1.models;

import java.util.Date;

public class GroupAccount {
    private Group group;
    private Account account;
    private Date joinDate;

    public GroupAccount(Group group, Account account, Date joinDate) {
        this.group = group;
        this.account = account;
        this.joinDate = joinDate;
    }

    public GroupAccount() {}

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}
