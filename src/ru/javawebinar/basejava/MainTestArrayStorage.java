package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ArrayStorage;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test ru.javawebinar.basejava.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    static final Storage SORTED_STORAGE = new SortedArrayStorage();

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

        ////////////////// TEST SortedArrayStorage ////////////////////
        System.out.println(" -- SORTED ARRAY STORAGE --\n");

        //// save()
        SORTED_STORAGE.save(r3);
        SORTED_STORAGE.save(r1);
        SORTED_STORAGE.save(r2);
        printAllSorted();
        System.out.println("Size: " + SORTED_STORAGE.size());

        //// get()
        System.out.println("\nGet uuid1: " + SORTED_STORAGE.get(r1.getUuid()));
        System.out.println("Get uuid2: " + SORTED_STORAGE.get(r2.getUuid()));
        System.out.println("Get uuid3: " + SORTED_STORAGE.get(r3.getUuid()));
        System.out.println("Get dummy: " + SORTED_STORAGE.get("dummy"));

        //// save() duplicate
        System.out.println("\nSave DUB of uuid2");
        Resume r2_dub = new Resume();
        r2_dub.setUuid("uuid2");
        SORTED_STORAGE.save(r2_dub);
        printAllSorted();

        //// update()
        System.out.println("\nUpdate uuid2 : "+System.identityHashCode(r2));
        System.out.println("....on uuid2 : " + System.identityHashCode(r2_dub));
        SORTED_STORAGE.update(r2_dub);
        printAllSorted();

        //// delete()
        System.out.println("\nDelete r1");
        SORTED_STORAGE.delete(r1.getUuid());
        printAllSorted();
        System.out.println("Delete r3");
        SORTED_STORAGE.delete(r3.getUuid());
        printAllSorted();

        //// clear()
        System.out.println("Clear. Before:");
        SORTED_STORAGE.save(r1);
        SORTED_STORAGE.save(r3);
        printAllSorted();
        System.out.println("Clear. After:");
        SORTED_STORAGE.clear();
        printAllSorted();
        System.out.println("Size: " + SORTED_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }

    static void printAllSorted() {
        System.out.println("\nGet All sorted");
        for (Resume r : SORTED_STORAGE.getAll()) {
            System.out.println(r + " : " + System.identityHashCode(r));
        }
    }
}
