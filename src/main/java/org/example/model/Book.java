package org.example.model;

import java.util.Objects;

public class Book implements Identifiable {
    private Integer id;
    private String title;
    private String author;
    private int year;
    private BookStatus status;
    //private boolean isAvailable;

    public Book(Integer id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = BookStatus.AVAILABLE;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public BookStatus getStatus() {
        return status;
    }

    public boolean isAvailable() {
        return this.status == BookStatus.AVAILABLE;
    }

    public BookStatus borrow() {
        if (status != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Книгу нельзя выдать");
        }
        this.status = BookStatus.BORROWED;
        return BookStatus.BORROWED;
    }

    public BookStatus giveBack() {
        if (status != BookStatus.BORROWED) {
            throw new IllegalStateException("Книгу нельзя вернуть");
        }
        this.status = BookStatus.AVAILABLE;
        return BookStatus.AVAILABLE;
    }

    public void sendToRepair() {
        if (status != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Книгу нельзя сдать в ремонт");
        }
        this.status = BookStatus.IN_REPAIR;
    }

    public void finishRepair() {
        if (status != BookStatus.IN_REPAIR) {
            throw new IllegalStateException("Книга не в ремонте ");
        }
        this.status = BookStatus.AVAILABLE;
    }



    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", status=" + status +
                '}';
    }
}
