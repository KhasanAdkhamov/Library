package org.example.task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PracticeAdapter {

    public static Map<String, Object> check(List<Integer> before, List<Integer> after) {
        Shelf shelf = new Shelf();
        for (Integer id : before) {
            shelf.add(new Book(id, "Книга " + id, "Автор", 2000));
        }
        List<Book> snapShot = shelf.snapShot();
        List<Book> view = shelf.view();
        for (Integer id : after) {
            shelf.add(new Book(id, "Книга " + id, "Автор", 2000));
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("snapshot", ids(snapShot));
        result.put("view", ids(view));
        result.put("snapshotReadOnly", readOnly(snapShot));
        result.put("viewReadOnly", readOnly(view));
        result.put("shelfAfterAttempts", ids(shelf.view()));
        return result;
    }

    private static List<Integer> ids(List<Book> books) {
        List<Integer> ids = new ArrayList<>();
        for (Book book : books) {
            ids.add(book.getId());
        }
        return ids;
    }

    private static boolean readOnly(List<Book> books) {
        try {
            books.add(new Book(-1, "Лишняя", "Автор", 2000));
            return false;
        } catch (UnsupportedOperationException expected) {
            return true;
        }
    }
}
