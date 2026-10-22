package org.example.service;

import org.example.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.NoSuchElementException;

//бросать соответствующее исключение
//добавить объекты, которые используются в тестах
public class LibraryServiceTest {
    private LibraryService libraryService;
    private Repository<Book> bookRepository;
    private Repository<User> userRepository;
    private User user1;
    private User user2;
    private User user3;
    private Book book1;
    private Book book2;
    private Book book3;


    @BeforeEach
    void setUp() {
        bookRepository = new Repository<>();
        userRepository = new Repository<>();
        libraryService = new LibraryService();
        libraryService.setBooks(bookRepository);
        libraryService.setUsers(userRepository);
        book1 = new Book(1, "война и мир", "Л.Т", 1970);
        book2 = new Book(2, "гарри поттер", "А.Г", 2000);
        book3 = new Book(3, "Сила слова", "Г.Д", 2010);
        user1 = new User(1, "Иван", 18);
        user2 = new User(2, "Лера", 22);
        user3 = new User(3, "Антон", 25);
        bookRepository.add(book1);
        bookRepository.add(book2);
        bookRepository.add(book3);
        userRepository.add(user1);
        userRepository.add(user2);
        userRepository.add(user3);
    }

    @Test
    void successfullBorrow() {
        libraryService.borrowBook(user1.getId(), book1.getId());
        Assertions.assertTrue(user1.hasBook(book1));
    }

    @Test
    void borrowBookWhenBorrowed() {
        libraryService.borrowBook(user1.getId(), book1.getId());
        Assertions.assertThrows(IllegalStateException.class, ()-> {
            libraryService.borrowBook(user2.getId(), book1.getId());
        });
    }

    @Test
    void ifNullUser() {
        Assertions.assertThrows(NoSuchElementException.class, ()-> {
            libraryService.returnBook(4, 1);
        });
    }

    @Test
    void ifNullBook() {
        Assertions.assertThrows(NoSuchElementException.class, ()-> {
            bookRepository.getById(5);
        });
    }

    @Test
    void returnBook() {
        libraryService.borrowBook(user1.getId(), book1.getId());
        libraryService.returnBook(user1.getId(), book1.getId());
        Assertions.assertFalse(user1.hasBook(book1));
    }
    @Test
    @DisplayName("у тебя нет такой книги для возврата")
    void returnBookIfYouHaveNotIt() {
        libraryService.borrowBook(user1.getId(), book1.getId());
        Assertions.assertThrows(IllegalStateException.class, ()-> {
           libraryService.returnBook(user1.getId(), book3.getId());
        });
    }

    @Test
    void bookOfAnotherReaderIsNotReturned() {
        libraryService.borrowBook(user1.getId(), book1.getId());
        Assertions.assertThrows(IllegalStateException.class, ()-> {
            libraryService.returnBook(user2.getId(), book1.getId());
        });
        Assertions.assertEquals(BookStatus.BORROWED, book1.getStatus());
        Assertions.assertTrue(user1.hasBook(book1));
    }

    @Test
    void deleteIfBookBorrowed() {
        libraryService.borrowBook(user1.getId(), book1.getId());
        Assertions.assertThrows(IllegalStateException.class, () -> {
            libraryService.deleteBook(book1.getId());
        });
    }

    @Test
    void deleteIfUserHasBook() {
        libraryService.borrowBook(user2.getId(), book1.getId());
        Assertions.assertThrows(IllegalStateException.class, () -> {
            libraryService.deleteUser(user2.getId());
        });
    }

    @Test
    void deleteUserHasNotBook() {
        libraryService.deleteUser(user1.getId());
        Assertions.assertThrows(NoSuchElementException.class, ()-> {
            userRepository.getById(user1.getId());
        });
    }

    @ParameterizedTest
    @CsvSource({"1, война и мир, Л.Т, 1970", "5, гордость и предупреждение, Д.А, 1999", "7, психология лжи, П.Э, 2002"})
    void repositoryTest(Integer id, String title, String author, int year) {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            bookRepository.add(new Book(id, title, author, year));
        });
    }

    @ParameterizedTest
    @CsvSource({"6", "5", "7"})
    void testRepositoryIfNotId(Integer id) {
        Assertions.assertThrows(NoSuchElementException.class, ()-> {
           userRepository.getById(id);
        });
    }

    @Test
    void testDeleteBookIfItInRepair() {
        libraryService.sendToRepair(book1.getId());
        libraryService.deleteBook(book1.getId());
        Assertions.assertThrows(NoSuchElementException.class, ()-> {
            bookRepository.getById(book1.getId());
        });
    }

//    @Test
//    void testFindAll() {
//        userRepository.findAll().;
//    }


//    @Test
//    void testBorrowBook() {
//        int userId = 1;
//        int bookId = 1;
//        libraryService.borrowBook(user1.getId(), bookId);
//        Assertions.assertFalse(libraryService.getBookById(bookId).isAvailable());
//    }








//    @Test
//    void testGetUserAndBook() {
//        Assertions.assertAll(
//                () -> Assertions.assertNotNull(libraryService.getUserById(1)),
//                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> {
//                    libraryService.getUserById(100);
//                }),
//                () -> Assertions.assertNotNull(libraryService.getBookById(1)),
//                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> {
//                    libraryService.getBookById(100);
//                })
//        );
//    }

//    @Test
//    void testIsAvailable() {
//        Assertions.assertTrue(libraryService.getBookById(1).isAvailable());
//    }

}
