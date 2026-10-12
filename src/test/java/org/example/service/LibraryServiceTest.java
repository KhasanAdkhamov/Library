package org.example.service;

import org.example.model.Book;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryServiceTest {
    private LibraryService libraryService;

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

    }
}
