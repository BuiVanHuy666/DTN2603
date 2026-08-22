package lesson_1.models;

import java.util.Date;

public class Question {
    private int id;
    private String content;
    private TypeQuestion type;
    private CategoryQuestion category;
    private Account creator;
    private Date createdAt;
}
