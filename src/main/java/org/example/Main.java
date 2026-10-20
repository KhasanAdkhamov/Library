package org.example;

import org.example.model.Book;
import org.example.model.Repository;
import org.example.model.Status;
import org.example.model.User;
import org.example.service.LibraryService;
import org.example.task.PracticeAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

public class Main {
    public static void main(String[] args) {
//        LibraryService libraryService = new LibraryService();
//        Book book = new Book(1, "война и мир", "Л.Т", 1970);
//        User user = new User(1, "Иван", 18);
//        //libraryService.addBook(book);
//        libraryService.addUser(user);
//        libraryService.addBook(book);
//        libraryService.borrowBook(1,1);
//        System.out.println(libraryService.getAllBooks());
//        System.out.println(libraryService.getAllUsers());
        Book book2 = new Book(2, "book2", "L.L", 2005);
        User user1 = new User(1, "Ivan", 22);
        User user2 = new User(2, "Антон", 30);

        LibraryService libraryService = new LibraryService();
        Repository<Book> bookRepository = new Repository<>();
        Repository<User> userRepository = new Repository<>();
        libraryService.setUsers(userRepository);
        libraryService.setBooks(bookRepository);
        bookRepository.add(book2);
        userRepository.add(user1);
        userRepository.add(user2);
        libraryService.borrowBook(user1.getId(), book2.getId());
        libraryService.returnBook(user1.getId(), book2.getId());


        //libraryService.borrowBook(user1.getId(), book2.getId());
//        libraryService.returnBook(user1.getId(), book2.getId());
//
//        Map<String, Object> check = PracticeAdapter.check(List.of(1, 2, 3), List.of(4, 5, 6));
//        System.out.println(check);
        List<Book> all = libraryService.getBooks().findAll();
        List<Book> newBooks = new ArrayList<>();
        for (Book book : all) {
            if (book.getStatus() == Status.AVAILABLE) {
                newBooks.add(book);
            }
        }
        List<Book> list = newBooks.stream().sorted().toList();
        System.out.println(list);

    }
}