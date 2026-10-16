package org.example.model;

import java.util.Objects;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private int year;
    private Status status;
    //private boolean isAvailable;

    public Book(Integer id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = Status.AVAILABLE;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status borrow() {
        if (status != Status.AVAILABLE) {
            throw new IllegalStateException("Книгу нельзя выдать");
        }
        return Status.BORROWED;
    }

    public Status giveBack() {
        if (status != Status.BORROWED) {
            throw new IllegalStateException("Книгу нельзя вернуть");
        }
        return Status.AVAILABLE;
    }

    public Status sendToRepair() {
        if (status != Status.AVAILABLE) {
            throw new IllegalStateException("Книгу нельзя сдать в ремонт");
        }
        return Status.IN_REPAIR;
    }

    public Status finishRepair() {
        if (status != Status.IN_REPAIR) {
            throw new IllegalStateException("Книга не в ремонте ");
        }
        return Status.AVAILABLE;
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
