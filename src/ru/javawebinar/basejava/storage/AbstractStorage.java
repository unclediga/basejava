package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (!isKeyExists(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(resume, searchKey);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isKeyExists(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isKeyExists(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertElement(resume,searchKey);
        }
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isKeyExists(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        deleteElement(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listElements = getListElements();
        Collections.sort(listElements);
        return new ArrayList<>(listElements);
    }

    protected abstract void deleteElement(Object searchKey);

    protected abstract void insertElement(Resume resume, Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract Resume getElement(Object key);

    protected abstract void updateElement(Resume resume, Object searchKey);

    protected abstract boolean isKeyExists(Object searchKey);

    protected abstract List<Resume> getListElements();
}
