package org.example.service;

import org.example.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

//если у нас id = null и книга без id, то мы без бросаем IllegalArgumentException
//если у нас нет объекта с таким id NoSuchElementException
//если книга уже выдана IllegalStateException

//доработать статус книги в libraryService
public class LibraryService{
    private Repository<Book> books;
    private Repository<User> users;
    private Map<Integer, Loan> loans;

    public LibraryService() {
        this.books = new Repository<>();
        this.users = new Repository<>();
        loans = new HashMap<>();
    }

    public  void borrowBook(Integer userId, Integer bookId, LocalDate takenOn) {
        Book book = books.getById(bookId);
        User user = users.getById(userId);
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Книгу нельзя выдать");
        }
        Loan loan = new Loan(bookId, userId, takenOn);
        user.addBook(book);
        book.borrow();
        loans.put(bookId, loan);
        System.out.println(user.getName() + " выдана книга " + book.getTitle());
    }

    public void borrowBook(Integer userId, Integer bookId) {
        borrowBook(userId, bookId, LocalDate.now());
    }

    public Optional<Loan> loanOf(Integer bookId) {
        Loan loan = loans.get(bookId);
        return Optional.ofNullable(loan);
    }

    public void returnBook(Integer userId, Integer bookId) {
        Book book = books.getById(bookId);
        User user = users.getById(userId);
        if (!user.hasBook(book)) {
            throw new IllegalStateException("книги нет у пользователя");
        }
        if (loanOf(bookId).equals(Optional.empty())) {
            throw new NoSuchElementException("нет информации о дате и выдачи книги");
        }
        loans.remove(bookId);
        book.giveBack();
        user.removeBook(book);
        System.out.println(user.getName() + " вернул " + book.getTitle() + " книгу обратно ");
    }


    public void setBooks(Repository<Book> books) {
        this.books = books;
    }

    public void setUsers(Repository<User> users) {
        this.users = users;
    }

    public void deleteBook(Integer bookId) {
        Book book = books.getById(bookId);
        if (book.getStatus() == BookStatus.IN_REPAIR) {
            books.delete(bookId);
            return;
        }
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("нельзя удалить книгу, так как она у пользователя");
        }
        books.delete(bookId);
    }

    public void deleteUser(Integer userId) {
        User user = users.getById(userId);
        if (!user.getBooks().isEmpty()) {
            throw new IllegalStateException("у читателя есть книги");
        }
        users.delete(user.getId());
    }

    public void sendToRepair(Integer bookId) {
        Book book = books.getById(bookId);
        book.sendToRepair();
        System.out.println("книга в ремонте");
    }

    public void finishRepair(Integer bookId) {
        Book book = books.getById(bookId);
        book.finishRepair();
    }
}
