package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected Resume getElementByIndex(int index) {
        return storage[index];
    }

    @Override
    protected void updateElement(Resume resume, int index) {
        storage[index] = resume;
    }

    protected void fillDeletedElement(int index){
        shrinkArray(index);
        storage[size - 1] = null;
        size--;
    }

    protected void insertElement(Resume resume, int index){
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        };
        int insertIndex = expandArray(index);
        storage[insertIndex] = resume;
        size++;
    }

    protected abstract void shrinkArray(int index);

    protected abstract int expandArray(int index);

}
