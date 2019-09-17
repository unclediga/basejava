package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void update(Resume resume) {
        SK searchKey = getExistedSearchKey(resume.getUuid());
        updateElement(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        return getElement(searchKey);
    }

    @Override
    public void save(Resume resume) {
        SK searchKey = getNotExistedSearchKey(resume.getUuid());
        insertElement(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        deleteElement(searchKey);
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isKeyExists(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isKeyExists(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listElements = getListElements();
        Collections.sort(listElements);
        return listElements;
    }

    protected abstract void deleteElement(SK searchKey);

    protected abstract void insertElement(Resume resume, SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract Resume getElement(SK searchKey);

    protected abstract void updateElement(Resume resume, SK searchKey);

    protected abstract boolean isKeyExists(SK searchKey);

    protected abstract List<Resume> getListElements();
}
