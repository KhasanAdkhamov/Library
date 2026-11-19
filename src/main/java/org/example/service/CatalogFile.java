package org.example.service;

import org.example.model.Book;
import org.example.model.BookStatus;

public class CatalogFile {

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
}
