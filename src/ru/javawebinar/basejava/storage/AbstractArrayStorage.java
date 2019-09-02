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
    protected Resume getElement(Object key) {
        return storage[(Integer) key];
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected void fillDeletedElement(Object searchKey){
        shrinkArray((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void insertElement(Resume resume, Object searchKey){
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        int insertIndex = expandArray((Integer) searchKey);
        storage[insertIndex] = resume;
        size++;
    }

    protected abstract void shrinkArray(int index);

    protected abstract int expandArray(int index);

    @Override
    protected boolean isKeyExists(Object searchKey) {
        return (Integer) searchKey > -1;
    }
}
