package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected final Map<Integer, Resume> storage = new HashMap<>();

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
    protected void fillDeletedElement(int index) {
        storage.remove(index);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        storage.put(storage.size(), resume);
    }

    @Override
    protected int getIndex(String uuid) {
        for (Map.Entry<Integer, Resume> entry : storage.entrySet()) {
            if (entry.getValue().getUuid().equals(uuid)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    @Override
    protected Resume getElementByIndex(int index) {
        return storage.get(index);
    }

    @Override
    protected void updateElement(Resume resume, int index) {
        storage.put(index, resume);
    }
}
