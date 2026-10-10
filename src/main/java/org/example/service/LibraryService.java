package org.example.service;

import org.example.model.Book;
import org.example.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
//убрать bookToUser
//нет проверки при добавлении книги
//поменять сигнатуры методы
//создать собственное исключение под конкретную ситуацию
//если у нас id = null и книга без id, то мы без бросаем IllegalArgumentException
//если у нас нет объекта с таким id NoSuchElementException
//если книга уже выдана IllegalStateException
public class LibraryService {
    private Map<Integer, Book> books;
    private Map<Integer, User> users;

    public LibraryService() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void borrowBook(Integer userId, Integer bookId) {
        User user = getUserById(userId);
        Book book = getBookById(bookId);
        if (!book.isAvailable()) {
            throw new IllegalArgumentException("книги нет, " + user.getName() + " выбери другую");
        }
        book.setAvailable(false);
        user.addBook(book);
        System.out.println(user.getName() + " выдана книга " + book.getTitle());
    }

    public void returnBook(Integer userId, Integer bookId) {
        Book book = getBookById(bookId);
        User user = getUserById(userId);
        if (book.isAvailable()) {
            throw new RuntimeException("книгу не выдавалась");
        }
        if (!user.hasBook(book)) {
            throw new IllegalArgumentException("");
        }
        book.setAvailable(true);
        user.deleteBook(book);
        System.out.println(user.getName() + " вернул " + book.getTitle() + " книгу обратно ");
    }

    public Book getBookById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("нет такого id");
        }
        if (!books.containsKey(id)) {
            throw new NoSuchElementException("не cуществует объекта с таким id");
        }
        return books.get(id);
    }

    public User getUserById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("нет такого id");
        }
        if (!users.containsKey(id)) {
            throw new NoSuchElementException("не cуществует объекта с таким id");
        }
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

    public void deleteBook(Integer bookId) {
        Book book = getBookById(bookId);
        if (!book.isAvailable()) {
            throw new IllegalStateException("нельзя удалить книгу, так как она у пользователя");
        }
        books.remove(bookId);
    }

    public void deleteUser(Integer userId) {
        User user = getUserById(userId);
        if (!user.getBooks().isEmpty()) {
            throw new IllegalStateException("у читателя есть книги");
        }
        users.remove(user.getId());
    }
}
