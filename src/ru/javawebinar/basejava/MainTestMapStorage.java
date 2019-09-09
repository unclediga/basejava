package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;
import ru.javawebinar.basejava.storage.MapResumeStorage;
import ru.javawebinar.basejava.storage.MapUuidStorage;

public class MainTestMapStorage {

    public static void main(String[] args) {
        System.out.println("##### Map Uuid #####");
        TestMap(new MapUuidStorage());
        System.out.println("##### Map Resume #####");
        TestMap(new MapResumeStorage());
    }

    private static void TestMap(AbstractStorage storage) {
        Resume r1 = new Resume("uuid1", "Full Name1");
        Resume r2 = new Resume("uuid2", "Full Name2");
        Resume r3 = new Resume("uuid3", "Full Name3");

        storage.save(r1);
        storage.save(r2);
        storage.save(r3);

        printAll(storage);
        System.out.println("Size: " + storage.size());

        System.out.println("Get r1: " + storage.get(r1.getUuid()));
        System.out.println("Size: " + storage.size());

        System.out.println("Get dummy...");
        try {
            storage.get("dummy");
        } catch (Exception e) {
            e.printStackTrace();
        }
        printAll(storage);

        System.out.println("delete r1...");
        storage.delete(r1.getUuid());
        printAll(storage);

        System.out.println("clear...");
        storage.clear();
        printAll(storage);
        System.out.println("Size: " + storage.size());
    }

    private static void printAll(AbstractStorage storage) {
        System.out.println("-- Get All: --");
        for (Resume r : storage.getAllSorted()) {
            System.out.println(r);
        }
    }
}
