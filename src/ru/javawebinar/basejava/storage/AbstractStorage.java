package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isKeyEmpty(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(resume, searchKey);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isKeyEmpty(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return getElementByKey(searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (!isKeyEmpty(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertElement(resume,searchKey);
        }
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isKeyEmpty(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        fillDeletedElement(searchKey);
    }

    protected abstract void fillDeletedElement(Object searchKey);

    protected abstract void insertElement(Resume resume, Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract Resume getElementByKey(Object key);

    protected abstract void updateElement(Resume resume, Object searchKey);

    protected abstract boolean isKeyEmpty(Object searchKey);
}
