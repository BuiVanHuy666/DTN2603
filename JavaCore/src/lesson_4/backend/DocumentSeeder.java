package lesson_4.backend;

import lesson_4.entities.Book;
import lesson_4.entities.Magazine;
import lesson_4.entities.Newspaper;
import lesson_4.entities.baseclass.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentSeeder {

    public static List<Document> seed() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Book(
                "B001",
                "Kim Dong",
                100,
                "Nguyen Nhat Anh",
                250
        ));

        documents.add(new Book(
                "B002",
                "Tre Publishing House",
                150,
                "To Hoai",
                320
        ));

        documents.add(new Book(
                "B003",
                "Education Publishing House",
                200,
                "Nam Cao",
                280
        ));

        // Magazines
        documents.add(new Magazine(
                "M001",
                "Thanh Nien",
                500,
                101,
                9
        ));

        documents.add(new Magazine(
                "M002",
                "Tuoi Tre",
                300,
                205,
                8
        ));

        // Newspapers
        documents.add(new Newspaper(
                "N001",
                "Thanh Nien",
                1000,
                LocalDate.of(2026, 9, 1)
        ));

        documents.add(new Newspaper(
                "N002",
                "Tuoi Tre",
                800,
                LocalDate.of(2026, 9, 5)
        ));

        return documents;
    }
}