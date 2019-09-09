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
        for (int index = 0; index < storage.size(); index++) {
            Resume resume = storage.get(index);
            if (resume.getUuid().equals(uuid))
                return index;
        }
        return null;
    }

    @Override
    protected List<Resume> getListElements() {
        return storage;
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.add((Integer) searchKey, resume);
    }

    @Override
    protected boolean isKeyExists(Object searchKey) {
        return searchKey != null;
    }
}
