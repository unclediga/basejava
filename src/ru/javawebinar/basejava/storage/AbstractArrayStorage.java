package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else {
            updateElement(index, r);
        }
    }

    @Override
    public void save(Resume r) {
        if (size == 0) {
            storage[0] = r;
            size = 1;
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            int index = getIndex(r.getUuid());
            if (index > 0) {
                System.out.println("Resume " + r.getUuid() + " already exist");
            } else {
                saveElement(index, r);
                size++;
            }
        }

    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            deleteElement(index);
            size--;
        }

    }

    protected abstract void updateElement(int index, Resume r);

    protected abstract void saveElement(int index, Resume r);

    protected abstract void deleteElement(int index);

    protected abstract int getIndex(String uuid);
}
