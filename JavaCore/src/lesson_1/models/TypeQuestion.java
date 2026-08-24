package lesson_1.models;

import lesson_1.enums.QuestionType;

public class TypeQuestion {
    private int id;
    private QuestionType name;

    public TypeQuestion(int id, QuestionType name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuestionType getName() {
        return name;
    }

    public void setName(QuestionType name) {
        this.name = name;
    }
}
