package org.example.service;

import org.example.model.Book;
import org.example.model.BookStatus;
import org.example.model.Loan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatalogFile {
    private static final String HEADER = "id;title;author;year;status";

    public static String toLine(Book book) {
        return book.getId() + ";" + book.getTitle() + ";" + book.getAuthor() + ";" + book.getYear() + ";" + book.getStatus();
    }

    public static Book parse(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Пустая строка");
        }
        String[] parts = line.split(";", -1);
        if (parts.length != 5) {
            throw new IllegalArgumentException("Ожидали 5 полей, а в строке " + parts.length + ": " + line);
        }
        try {
            Integer id = Integer.valueOf(parts[0].trim());
            String title = parts[1].trim();
            String author = parts[2].trim();
            int year = Integer.parseInt(parts[3].trim());
            BookStatus bookStatus = BookStatus.valueOf(parts[4].trim());
            Book book = new Book(id, title, author, year);
            if (bookStatus == BookStatus.BORROWED) {
                book.borrow();
            }
            if (bookStatus == BookStatus.IN_REPAIR) {
                book.sendToRepair();
            }
            return book;
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("Не число в строке: " + line);
        }
    }

    public static void save(Path path, List<Book> list) throws IOException{
        Files.write(path, write(list));

    }

    public static List<String> write(List<Book> list) throws IOException {
        return new ArrayList<>();
    }

    public static List<Book> load(Path path) {
        return new ArrayList<>();
    }

    public static List<Book> read(List<String> list) {
        List<Book> listBook = new ArrayList<>();
        if(list == null || list.isEmpty()) {
            throw new IllegalArgumentException("список пустой");
        }
        if(!list.getFirst().strip().equals(HEADER)) {
            throw new IllegalArgumentException("Нет заголовка");
        }
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) == null || list.get(i).strip().isEmpty()) {
                continue;
            }
            try {
                Book book = parse(list.get(i));
                listBook.add(book);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Строка " + (i +1) + " " + ex.getMessage());
            }
        }
        return List.copyOf(listBook);
    }
}
