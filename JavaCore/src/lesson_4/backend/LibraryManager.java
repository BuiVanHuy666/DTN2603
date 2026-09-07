package lesson_4.backend;

import lesson_4.entities.Book;
import lesson_4.entities.Magazine;
import lesson_4.entities.Newspaper;
import lesson_4.entities.baseclass.Document;
import lesson_4.backend.interfaces.Manageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibraryManager implements Manageable {
    private final Scanner scanner = new Scanner(System.in);
    private List<Document> documents;

    public LibraryManager() {
        documents = DocumentSeeder.seed();
    }


    @Override
    public void store() {
        System.out.println("===Thêm tài liệu===");

        System.out.println("Nhập mã:");
        String id = scanner.nextLine();

        System.out.println("Nhập tên nhà xuất bản:");
        String publisher = scanner.nextLine();

        System.out.println("Nhập số bản phát hành:");
        int circulation = Integer.parseInt(scanner.nextLine());

        System.out.println("Nhập loại tài liệu (1. Sách/2. Tạp chí/3. Báo):");
        String type = scanner.nextLine();

        switch(type) {
            case "1":
                System.out.println("Nhập tên tác giả:");
                String author = scanner.nextLine();

                System.out.println("Nhập số trang:");
                int pageCount = Integer.parseInt(scanner.nextLine());

                Document book = new Book(id, publisher, circulation, author, pageCount);
                documents.add(book);
                break;
            case "2":
                System.out.println("Nhập số phát hành:");
                int issueNumber = Integer.parseInt(scanner.nextLine());

                System.out.println("Nhập tháng phát hành:");
                int issueMonth = Integer.parseInt(scanner.nextLine());

                Document magazine = new Magazine(id, publisher, circulation, issueNumber, issueMonth);
                documents.add(magazine);
                break;
            case "3":
                System.out.print("Nhập ngày phát hành (định dạng yyyy-MM-dd, ví dụ: 2026-09-07): ");
                LocalDate publishDate = LocalDate.parse(scanner.nextLine().trim());
                Document newspaper = new Newspaper(id, publisher, circulation, publishDate);
                documents.add(newspaper);
                break;
            default:
                System.out.println("Loại tài liệu không hợp lệ.");
                return;
        }

        System.out.println();
    }


    @Override
    public void destroy() {
        System.out.println("Nhập mã tài liệu cần xoá:");
        String documentId = scanner.nextLine();
        documents.removeIf(document -> document.getDocumentId().equals(documentId));
    }

    @Override
    public void show() {
        System.out.println("===Tìm tài liệu theo loại");
        System.out.println("Nhập loại tài liệu (1. Sách/2. Tạp chí/3. Báo):");
        String type = scanner.nextLine();
        switch(type) {
            case "1":
                index(documents.stream().filter(doc -> doc instanceof Book).collect(Collectors.toList()));
                break;
            case "2":
                index(documents.stream().filter(doc -> doc instanceof Magazine).collect(Collectors.toList()));
                break;
            case "3":
                index(documents.stream().filter(doc -> doc instanceof Newspaper).collect(Collectors.toList()));
                break;
            default:
                System.out.println("Loại tài liệu không hợp lệ.");
        }
    }

    @Override
    public void index() {
        index(this.documents);
    }

    public void index(List<Document> targetDocuments) {
        if (targetDocuments == null || targetDocuments.isEmpty()) {
            System.out.println("Không có tài liệu nào để hiển thị.");
            return;
        }

        String border = "+----------+-------------------------+--------------------+------------+----------------------------------------+";
        String format = "| %-8s | %-23s | %-18s | %-10s | %-38s |%n";

        System.out.println(border);
        System.out.printf(format, "Mã", "Tên nhà xuất bản", "Số bản phát hành", "Loại", "Thông tin thêm");
        System.out.println(border);

        for (Document document : targetDocuments) {
            String type;
            String extraInfo;

            if (document instanceof Book book) {
                type = "Book";
                extraInfo = "Author: " + book.getAuthor() + ", Pages: " + book.getPageCount();
            } else if (document instanceof Magazine magazine) {
                type = "Magazine";
                extraInfo = "Issue: " + magazine.getIssueNumber() + ", Month: " + magazine.getIssueMonth();
            } else if (document instanceof Newspaper newspaper) {
                type = "Newspaper";
                extraInfo = "Date: " + newspaper.getPublishDate();
            } else {
                type = "Unknown";
                extraInfo = "";
            }

            System.out.printf(format,
                    document.getDocumentId(),
                    document.getPublisher(),
                    String.valueOf(document.getCirculation()),
                    type,
                    extraInfo);
        }

        System.out.println(border);
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}