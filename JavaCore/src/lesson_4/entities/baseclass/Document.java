package lesson_4.entities.baseclass;

public class Document {
    private String documentId;
    private String publisher;
    private int circulation;

    public Document(String id, String title, int circulation) {
        this.documentId = id;
        this.publisher = title;
        this.circulation = circulation;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getCirculation() {
        return circulation;
    }

    public void setCirculation(int circulation) {
        this.circulation = circulation;
    }
}