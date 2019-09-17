package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected void deleteElement(Integer index) {
        // here needed exactly "list.remove(int)"
        storage.remove((int) index);
    }

    @Override
    protected void insertElement(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int index = 0; index < storage.size(); index++) {
            Resume resume = storage.get(index);
            if (resume.getUuid().equals(uuid))
                return index;
        }
        return null;
    }

    @Override
    protected List<Resume> getListElements() {
        return new ArrayList<>(storage);
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage.get((int) index);
    }

    @Override
    protected void updateElement(Resume resume, Integer index) {
        storage.add((int) index, resume);
    }

    @Override
    protected boolean isKeyExists(Integer index) {
        return index != null;
    }
}
