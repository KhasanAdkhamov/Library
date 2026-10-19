package org.example.model;

import java.util.*;

public class User implements Identifiable{
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

    @Override
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
        return Collections.unmodifiableSet(books);
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println(book.getId() + " " + book.getTitle() + " книга добавлена");
    }

    public boolean hasBook(Book book) {
        return books.contains(book);
    }

    public void removeBook(Book book) {
        if (!books.contains(book)) {
            throw new IllegalArgumentException("нет данной книги для удаления");
        }
        books.remove(book);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age;
    }
}
