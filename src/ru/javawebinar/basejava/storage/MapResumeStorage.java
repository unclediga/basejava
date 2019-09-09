package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
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
        storage.remove(((Resume)searchKey).getUuid());
    }

    @Override
    protected void insertElement(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume getElement(Object key) {
        return storage.get(((Resume)key).getUuid());
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.put(((Resume)searchKey).getUuid(), resume);
    }

    @Override
    protected boolean isKeyExists(Object searchKey) {
        Resume resume = (Resume) searchKey;
        return resume != null && storage.containsKey(resume.getUuid());
    }

    @Override
    protected List<Resume> getListElements() {
        return new ArrayList<>(storage.values());
    }
}