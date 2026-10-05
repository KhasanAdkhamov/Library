package org.example.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private Integer id;
    private String name;
    private int age;
    private Set<Book> books;

    public User(Integer id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.books = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println(book.getId() + " " + book.getTitle() + " книга добавлена");
    }

    public void deleteBook(Book book) {
        books.remove(book);
        System.out.println(book.getTitle() + " книга удалена");
    }
}
