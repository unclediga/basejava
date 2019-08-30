package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

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
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected void fillDeletedElement(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void insertElement(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume getElementByKey(Object key) {
        return storage.get(key);
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected boolean isKeyEmpty(Object searchKey) {
        return searchKey == null;
    }
}
