package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.MapStorage;

public class MainTestMapStorage {
    static final MapStorage ARRAY_STORAGE = new MapStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy...");
        try {
            ARRAY_STORAGE.get("dummy");
        } catch (Exception e) {
            e.printStackTrace();
        }
        printAll();

        System.out.println("delete r1...");
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();

        System.out.println("clear...");
        ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("-- Get All: --");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
