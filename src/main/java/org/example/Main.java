package org.example;

import org.example.model.Book;
import org.example.model.User;
import org.example.service.LibraryService;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        Book book = new Book(1, "война и мир", "Л.Т", 1970);
        User user = new User(1, "Иван", 18);
        //libraryService.addBook(book);
        libraryService.addUser(user);
        libraryService.addBook(book);
        libraryService.borrowBook(1,1);
        System.out.println(libraryService.getAllBooks());
        System.out.println(libraryService.getAllUsers());
    }
}