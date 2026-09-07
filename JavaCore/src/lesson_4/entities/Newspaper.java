package lesson_4.entities;

import lesson_4.entities.baseclass.Document;
import java.time.LocalDate;

public class Newspaper extends Document {
    private LocalDate publishDate;

    public Newspaper(String id, String publisher, int circulation, LocalDate publishDate) {
        super(id, publisher, circulation);
        this.publishDate = publishDate;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
}