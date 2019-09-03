package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected void deleteElement(Object searchKey) {
        // here needed exactly "list.remove(int)"
        storage.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected void insertElement(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int index = 0; index < size(); index++) {
            Resume resume = storage.get(index);
            if (resume.getUuid().equals(uuid))
                return index;
        }
        return -1;
    }

    @Override
    protected Resume getElement(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.add((Integer) searchKey, resume);
    }

    @Override
    protected boolean isKeyExists(Object searchKey) {
        return ((Integer) searchKey) > -1;
    }
}
