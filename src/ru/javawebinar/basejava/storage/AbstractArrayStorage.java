package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

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

    @Override
    protected Resume getElement(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void updateElement(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    protected void deleteElement(Object index) {
        shrinkArray((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void insertElement(Resume resume, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        int insertIndex = expandArray((int) index);
        storage[insertIndex] = resume;
        size++;
    }

    protected abstract void shrinkArray(int index);

    protected abstract int expandArray(int index);

    @Override
    protected boolean isKeyExists(Object index) {
        return (int)index > -1;
    }

    @Override
    protected List<Resume> getListElements() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
