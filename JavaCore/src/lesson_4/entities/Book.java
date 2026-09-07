package lesson_4.entities;

import lesson_4.entities.baseclass.Document;

public class Book extends Document {
    private String author;
    private int pageCount;

    public Book(String id, String publisher, int circulation, String author, int pageCount) {
        super(id, publisher, circulation);
        this.author = author;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}