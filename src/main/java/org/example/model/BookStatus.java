package org.example.model;

/** Состояние книги в библиотеке. Имя константы — для кода, title — для человека. */
public enum BookStatus {
    AVAILABLE("доступна"),
    BORROWED("выдана"),
    IN_REPAIR("в ремонте");

    private final String title;

    BookStatus(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }
}
