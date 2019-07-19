package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    // Для дальнейших проверок на переполнение выносим размер массива в константу.
    final int STORAGE_SIZE = 10000;
    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int size = 0;

    public void clear() {
        // Используем подходящий метод из java.util.Arrays
        Arrays.fill(storage, null);
        size = 0;

    }

    public void save(Resume r) {
        int i = getIdx(r.getUuid());
        if (i != -1) {
            System.out.println("Resume present in Storage!");
            return;
        } else if (size + 1 > STORAGE_SIZE) {
            System.out.println("Storage overflow!");
            return;
        }
        storage[size] = r;
        size++;

    }

    public Resume get(String uuid) {
        int i = getIdx(uuid);
        if (i == -1) {
            System.out.println("Resume not present in Storage!");
            return null;
        }
        return storage[i];

    }

    public void delete(String uuid) {
        int i = getIdx(uuid);
        if (i == -1) {
            System.out.println("Resume not present in Storage!");
            return;
        }
        storage[i] = storage[size - 1];
        storage[size - 1] = null;
        size--;

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public Resume[] getAll() {
        // Используем подходящий метод из java.util.Arrays
        Resume[] result = Arrays.copyOf(storage, size);
        return result;

    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int idx = getIdx(resume.getUuid());
        if (idx == -1) {
            System.out.println("Resume not present in Storage!");
            return;
        }
        storage[idx] = resume;
    }

    // Избавляемся от дублирования кода
    private int getIdx(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}