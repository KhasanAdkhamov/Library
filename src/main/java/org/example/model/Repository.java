package org.example.model;

import java.util.*;

//метод findAll
public class Repository <T extends Identifiable>{
    private Map<Integer, T> elements;

    public Repository() {
        this.elements = new LinkedHashMap<>();
    }

    public T getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("нет такого id");
        }
        if (!elements.containsKey(id)) {
            throw new NoSuchElementException("не cуществует объекта с таким id");
        }
        return elements.get(id);
    }

    public void add(T element) {
        if (element == null || element.getId() == null) {
            throw new IllegalArgumentException("книга и ее id обязательны");
        }
        if (elements.containsKey(element.getId())) {
            throw new IllegalStateException("книга с id " + element.getId() + " уже есть");
        }
        elements.put(element.getId(), element);
    }

    public void delete(Integer id) {
        T element = getById(id);
        elements.remove(id);
    }

    public List<T> findAll() {
        List<T> list = elements.values().stream()
                .sorted(Comparator.comparing(element -> element.getId()))
                .toList();
        return List.copyOf(list);
    }
}
