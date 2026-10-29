package org.example.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Loan(Integer bookId, Integer userId, LocalDate takenOn) {
    public Loan {
        if (bookId == null) {
            throw new IllegalArgumentException("id  книги обязательно");
        }
        if (userId == null) {
            throw new IllegalArgumentException("id пользователя обязательно");
        }
        if (takenOn == null) {
            throw new IllegalArgumentException("дата выдачи обязательна");
        }
    }
    public LocalDate dueDate() {
        return takenOn.plusDays(14);
    }

    public long daysLate(LocalDate today) {
        return Math.max(0, ChronoUnit.DAYS.between(today, dueDate()));
    }

    public boolean isOverdue(LocalDate today) {
        return today.isAfter(dueDate());
    }
}
