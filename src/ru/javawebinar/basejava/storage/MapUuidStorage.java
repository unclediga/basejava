package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

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
        storage.remove(searchKey);
    }

    @Override
    protected void insertElement(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected boolean isKeyExists(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected List<Resume> getListElements() {
        return new ArrayList<>(storage.values());
    }
}
