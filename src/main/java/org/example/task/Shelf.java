package org.example.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shelf {
    private final List<Book> books = new ArrayList<>();

    public void add(Book book) {
        books.add(book);
    }

    public List<Book> snapShot() {
        if (books.isEmpty()) {
            return new ArrayList<>();
        }
        return List.copyOf(books);
    }

    public List<Book> view() {
        if (books.isEmpty()) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(books);
    }
}
