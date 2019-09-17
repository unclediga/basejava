package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
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
    protected void deleteElement(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void insertElement(Resume resume, String searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume getElement(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void updateElement(Resume resume, String searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected boolean isKeyExists(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected List<Resume> getListElements() {
        return new ArrayList<>(storage.values());
    }
}
