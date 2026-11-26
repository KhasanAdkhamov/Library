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
import java.util.Map;

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

    public static List<String> write(List<Book> list) {
        List<String> listBook = new ArrayList<>();
        listBook.add(HEADER);
        for (Book book : list) {
            listBook.add(toLine(book));
        }
        return listBook;
    }

    public static List<Book> load(Path path) throws IOException {
        return read(Files.readAllLines(path));
    }

    public static List<Book> read(List<String> list) {
        List<Book> listBook = new ArrayList<>();
        Map<Integer, String> idBookMap = new HashMap<>();
        if(list == null || list.isEmpty()) {
            throw new IllegalArgumentException("список пустой");
        }
        if(!list.getFirst().strip().equals(HEADER)) {
            throw new IllegalArgumentException("Нет заголовка");
        }
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) == null || list.get(i).isBlank()) {
                continue;
            }
            Book book;
            try {
                book = parse(list.get(i));
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Строка " + (i +1) + " " + ex.getMessage());
            }
            if (idBookMap.containsKey(book.getId())) {
                throw new IllegalArgumentException("Строка " + (i + 1) + ": id " + book.getId() + " уже есть в строке " + idBookMap.get(book.getId()));
            }
            idBookMap.put(book.getId(), String.valueOf(i + 1));
            listBook.add(book);
        }
        return List.copyOf(listBook);
    }
}
