package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void deleteElement(Resume searchKey) {
        storage.remove(((Resume)searchKey).getUuid());
    }

    @Override
    protected void insertElement(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume getElement(Resume searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void updateElement(Resume resume, Resume searchKey) {
        storage.put(((Resume)searchKey).getUuid(), resume);
    }

    @Override
    protected boolean isKeyExists(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> getListElements() {
        return new ArrayList<>(storage.values());
    }
}
