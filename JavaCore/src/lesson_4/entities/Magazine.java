package lesson_4.entities;

import lesson_4.entities.baseclass.Document;

public class Magazine extends Document {
    private int issueNumber;
    private int issueMonth;

    public Magazine(String id, String publisher, int circulation, int issueNumber, int issueMonth) {
        super(id, publisher, circulation);
        this.issueNumber = issueNumber;
        this.issueMonth = issueMonth;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public int getIssueMonth() {
        return issueMonth;
    }

    public void setIssueMonth(int issueMonth) {
        this.issueMonth = issueMonth;
    }
}
