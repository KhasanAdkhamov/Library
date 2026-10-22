package org.example;

import org.example.model.*;
import org.example.service.LibraryService;
import org.example.service.Repository;

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



    }
}