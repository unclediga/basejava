package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    // Для дальнейших проверок на переполнение выносим размер массива в константу.
    final static private int STORAGE_SIZE = 10_000;
    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int size = 0;

    public void clear() {
        // Используем подходящий метод из java.util.Arrays
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int idx = getIdx(resume.getUuid());
        if (idx != -1) {
            System.out.println("Resume present in Storage!");
            return;
        } else if (size >= STORAGE_SIZE) {
            System.out.println("Storage overflow!");
            return;
        }
        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        int idx = getIdx(uuid);
        if (idx == -1) {
            System.out.println("Resume not present in Storage!");
            return null;
        }
        return storage[idx];
    }

    public void delete(String uuid) {
        int idx = getIdx(uuid);
        if (idx == -1) {
            System.out.println("Resume not present in Storage!");
            return;
        }
        size--;
        storage[idx] = storage[size];
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public Resume[] getAll() {
        // Используем подходящий метод из java.util.Arrays
        return Arrays.copyOf(storage, size);
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