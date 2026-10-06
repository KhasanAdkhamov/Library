package org.example.service;

import org.example.model.Book;
import org.example.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LibraryService {
    private Map<Integer, Book> books;
    private Map<Integer, User> users;
    private Map<Integer, Integer> bookToUser;

    public LibraryService() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void borrowBook(User user, Book book) {
        if (!book.isAvailable()) {
            throw new NullPointerException("книги нет, " + user.getName() + " выбери другую");
        }
        book.setAvailable(false);
        user.addBook(book);
        bookToUser.put(book.getId(), user.getId());
        System.out.println(user.getName() + " выдана книга " + book.getTitle());
    }

    public void returnBook(User user, Book book) {
        if (book.isAvailable()) {
            throw new RuntimeException("книгу не выдавалась");
        }
        book.setAvailable(true);
        user.deleteBook(book);
        bookToUser.remove(book.getId());
        System.out.println(user.getName() + " вернул " + book.getTitle() + " книгу обратно ");
    }

    public Book getBookById(Integer id) {
        return books.get(id);
    }

    public User getUserById(Integer id) {
        return users.get(id);
    }

    public void addBook(Book book) {
        books.putIfAbsent(book.getId(), book);
    }

    public void addUser(User user) {
        users.putIfAbsent(user.getId(), user);
    }

    public void getAllUsers() {
        for (User value : users.values()) {
            System.out.println(value);
        }
    }

    public void getAllBooks() {
        for (Book value : books.values()) {
            System.out.println(value);
        }
    }

    public void deleteBook(Book book) {
        if (bookToUser.containsKey(book.getId()) || !book.isAvailable()) {
            throw new RuntimeException("нельзя удалить книгу, так как она у пользователя");
        }
        books.remove(book.getId());
    }

    public void deleteUser(User user) {
        if (bookToUser.containsValue(user)) {
            throw new RuntimeException("сначало пользователь должен вернуть книгу");
        }
        users.remove(user);
    }
}
