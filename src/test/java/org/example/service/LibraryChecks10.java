package org.example;

import org.example.model.Book;
import org.example.model.BookStatus;
import org.example.service.CatalogFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Проверки шага 1 домашней работы после десятого занятия.
 * Положи файл в src/test/java/org/example и запусти main зелёным треугольником.
 * Последняя строка вывода: «OK 9 из 9». Этот файл не меняй.
 */
public class LibraryChecks10 {
    private static int passed = 0;
    private static int total = 0;

    public static void main(String[] args) throws Exception {
        check("1. toLine: выданная книга", () -> {
            Book war = new Book(1, "Война и мир", "Лев Толстой", 1869);
            war.borrow();
            same("1;Война и мир;Лев Толстой;1869;BORROWED", CatalogFile.toLine(war));
        });

        check("2. parse: пробелы вокруг полей и статус", () -> {
            Book nose = CatalogFile.parse(" 2 ; Нос ; Николай Гоголь ; 1836 ; IN_REPAIR ");
            same(2, nose.getId());
            same("Нос", nose.getTitle());
            same("Николай Гоголь", nose.getAuthor());
            same(1836, nose.getYear());
            same(BookStatus.IN_REPAIR, nose.getStatus());
        });

        check("3. parse: четыре поля и точка с запятой в конце", () -> {
            rejects(() -> CatalogFile.parse("5;Идиот;Достоевский;1869"));
            rejects(() -> CatalogFile.parse("6;Нос;Гоголь;1836;AVAILABLE;"));
        });

        check("4. parse: не число и неизвестный статус", () -> {
            rejects(() -> CatalogFile.parse("x;Нос;Гоголь;1836;AVAILABLE"));
            rejects(() -> CatalogFile.parse("7;Нос;Гоголь;год;AVAILABLE"));
            rejects(() -> CatalogFile.parse("8;Нос;Гоголь;1836;LOST"));
        });

        check("5. write: заголовок и книги по порядку", () -> {
            Book war = new Book(1, "Война и мир", "Лев Толстой", 1869);
            Book nose = new Book(2, "Нос", "Николай Гоголь", 1836);
            same(List.of("id;title;author;year;status",
                            "1;Война и мир;Лев Толстой;1869;AVAILABLE",
                            "2;Нос;Николай Гоголь;1836;AVAILABLE"),
                    CatalogFile.write(List.of(war, nose)));
        });

        check("6. read: пустые строки пропущены, без заголовка отказ", () -> {
            List<Book> books = CatalogFile.read(List.of("id;title;author;year;status", "",
                    "1;Война и мир;Лев Толстой;1869;AVAILABLE", "   ",
                    "2;Нос;Николай Гоголь;1836;BORROWED"));
            same(2, books.size());
            same("Нос", books.get(1).getTitle());
            same(BookStatus.BORROWED, books.get(1).getStatus());
            rejects(() -> CatalogFile.read(List.of("1;Война и мир;Лев Толстой;1869;AVAILABLE")));
        });

        check("7. read: плохая строка названа по номеру в файле", () -> {
            try {
                CatalogFile.read(List.of("id;title;author;year;status",
                        "1;Нос;Гоголь;1836;AVAILABLE", "", "x;Нос;Гоголь;1836;AVAILABLE"));
            } catch (IllegalArgumentException error) {
                if (error.getMessage() == null || !error.getMessage().startsWith("Строка 4")) {
                    throw new IllegalStateException("текст ошибки должен начинаться со «Строка 4», а он такой: "
                            + error.getMessage());
                }
                return;
            }
            throw new IllegalStateException("ждали IllegalArgumentException, а метод ничего не бросил");
        });

        check("8. save и load: файл на диске и те же книги обратно", () -> {
            Path dir = Files.createTempDirectory("library-checks");
            Path file = dir.resolve("catalog.csv");
            Book war = new Book(1, "Война и мир", "Лев Толстой", 1869);
            Book nose = new Book(2, "Нос", "Николай Гоголь", 1836);
            nose.sendToRepair();
            CatalogFile.save(file, List.of(war, nose));
            same(3, Files.readAllLines(file).size());
            List<Book> loaded = CatalogFile.load(file);
            same(2, loaded.size());
            same("Война и мир", loaded.get(0).getTitle());
            same(BookStatus.IN_REPAIR, loaded.get(1).getStatus());
            Files.delete(file);
            Files.delete(dir);
        });

        check("9. read: две книги с одним id, названы обе строки", () -> {
            try {
                CatalogFile.read(List.of("id;title;author;year;status",
                        "2;Нос;Гоголь;1836;AVAILABLE", "", "2;Шинель;Гоголь;1842;AVAILABLE"));
            } catch (IllegalArgumentException error) {
                same("Строка 4: id 2 уже есть в строке 2", error.getMessage());
                return;
            }
            throw new IllegalStateException("ждали IllegalArgumentException, а метод ничего не бросил");
        });

        System.out.println("OK " + passed + " из " + total);
    }

    private interface Step {
        void run() throws Exception;
    }

    private static void check(String name, Step step) {
        total++;
        try {
            step.run();
            passed++;
            System.out.println("ok      " + name);
        } catch (Exception | AssertionError error) {
            System.out.println("НЕ ТАК  " + name + ": " + error);
        }
    }

    private static void same(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new IllegalStateException("ждали <" + expected + ">, получили <" + actual + ">");
        }
    }

    private static void rejects(Step step) throws Exception {
        try {
            step.run();
        } catch (IllegalArgumentException error) {
            return;
        }
        throw new IllegalStateException("ждали IllegalArgumentException, а метод ничего не бросил");
    }
}
