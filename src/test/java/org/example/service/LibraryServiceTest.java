package org.example.service;

import org.example.model.Book;
import org.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryServiceTest {
    private LibraryService libraryService;
    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService();

        libraryService.addBook(new Book(1, "война и мир", "Л.Т", 1970));
        libraryService.addBook(new Book(2, "гарри поттер", "А.Г", 2000));
        libraryService.addBook(new Book(3, "Сила слова", "Г.Д", 2010));

        libraryService.addUser(new User(1, "Иван", 18));
        libraryService.addUser(new User(2, "Лера", 22));
        libraryService.addUser(new User(3, "Антон", 25));
    }

    @Test
    void testBorrowBook() {
        int userId = 1;
        int bookId = 1;
        libraryService.borrowBook(userId, bookId);
        Assertions.assertFalse(libraryService.getBookById(bookId).isAvailable());
    }

    @Test
    void testBorrowBook2() {
        int userId = 1;
        int userId2 = 2;
        int bookId = 1;
        libraryService.borrowBook(userId, bookId);
        Assertions.assertThrows(RuntimeException.class, () -> {
            libraryService.borrowBook(userId2, bookId);
        });
    }

    @Test
    void testBorrowBook3() {
        int userId = 1;
        int bookId100= 100;
        int bookId2 = 2;
        Assertions.assertThrows(RuntimeException.class, () -> {
            libraryService.borrowBook(userId, bookId100);
        });
    }

    @Test
    void testReturnBook() {
        libraryService.borrowBook(1, 1);
        Assertions.assertThrows(RuntimeException.class, () -> {
           libraryService.returnBook(1, 2);
        });
    }

    @Test
    void testGetUserAndBook() {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(libraryService.getUserById(1)),
                () -> Assertions.assertThrows(RuntimeException.class, () -> {
                    libraryService.getUserById(100);
                }),
                () -> Assertions.assertNotNull(libraryService.getBookById(1)),
                () -> Assertions.assertThrows(RuntimeException.class, () -> {
                    libraryService.getBookById(100);
                })
        );
    }

    @Test
    void testIsAvailable() {
        Assertions.assertTrue(libraryService.getBookById(1).isAvailable());
    }
}
