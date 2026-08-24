package lesson_1.models;

import java.util.Date;

public class Question {
    private int id;
    private String content;
    private TypeQuestion type;
    private CategoryQuestion category;
    private Account creator;
    private Date createdAt;

    public Question(int id, String content, TypeQuestion type, CategoryQuestion category, Account creator, Date createdAt) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.category = category;
        this.creator = creator;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TypeQuestion getType() {
        return type;
    }

    public void setType(TypeQuestion type) {
        this.type = type;
    }

    public CategoryQuestion getCategory() {
        return category;
    }

    public void setCategory(CategoryQuestion category) {
        this.category = category;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
