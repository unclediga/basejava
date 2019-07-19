package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ArrayStorage;

/**
 * Test ru.javawebinar.basejava.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        //////////////////// Тесты для HW2  //////////////////////////////
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        printAll();

        System.out.println("\nTest: double save...");
        Resume r4 = new Resume();
        r4.setUuid("uuid3");
        ARRAY_STORAGE.save(r3);
        printAll();

        System.out.println("\nTest: delete not presented...");
        ARRAY_STORAGE.delete("uuid10");
        printAllwithHash();

        System.out.println("\nTest: update...");
        r4.setUuid("uuid2");
        ARRAY_STORAGE.update(r4);
        printAllwithHash();

        System.out.println("\nTest: update not presented...");
        Resume r5 = new Resume();
        r5.setUuid("uuid5");
        ARRAY_STORAGE.update(r5);
        printAll();

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }

    static void printAllwithHash() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r + ":" + r.hashCode());
        }
    }
}
