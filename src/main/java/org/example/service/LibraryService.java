package org.example.service;

import org.example.model.Book;
import org.example.model.User;

import java.util.*;

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
            throw new IllegalStateException("книги нет, " + user.getName() + " выбери другую");
        }
        book.setAvailable(false);
        user.addBook(book);
        System.out.println(user.getName() + " выдана книга " + book.getTitle());
    }

    public void returnBook(Integer userId, Integer bookId) {
        Book book = getBookById(bookId);
        User user = getUserById(userId);
        if (book.isAvailable()) {
            throw new IllegalStateException("книгу не выдавалась");
        }
        if (!user.hasBook(book)) {
            throw new IllegalStateException("");
        }
        book.setAvailable(true);
        user.removeBook(book);
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
        if (book == null || book.getId() == null) {
            throw new IllegalArgumentException("книга и ее id обязательны");
        }
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("книга с id " + book.getId() + " уже есть");
        }
        books.put(book.getId(), book);
    }

    public void addUser(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("пользователь и его id обязательны");
        }
        if (users.containsKey(user.getId())) {
            throw new IllegalStateException("уже есть такой пользователь");
        }
        users.put(user.getId(), user);
    }

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        return users.values().stream()
                .sorted(Comparator.comparing(User::getId))
                .toList();
    }

    public List<Book> getAllBooks() {
        if (books.isEmpty()) {
            return new ArrayList<>();
        }
        return books.values().stream()
                .sorted(Comparator.comparing(Book::getId))
                .toList();
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
