package lesson_1.models;

import java.util.Date;

public class Exam {
    private int id;
    private String code;
    private String title;
    private CategoryQuestion category;
    private int duration;
    private Account creator;
    private Date createdAt;
}
