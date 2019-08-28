package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

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
    protected void fillDeletedElement(int index) {
        storage.remove(index);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected int getIndex(String uuid) {
        Iterator it = storage.iterator();
        int index = 0;
        while (it.hasNext()) {
            Resume resume = (Resume) it.next();
            if (resume.getUuid().equals(uuid))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    protected Resume getElementByIndex(int index) {
        return storage.get(index);
    }

    @Override
    protected void updateElement(Resume resume, int index) {
        storage.add(index, resume);
    }
}
