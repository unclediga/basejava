package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 22.07.2016
 */
public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected void deleteElement(File searchKey) {

    }

    @Override
    protected void insertElement(Resume resume, File searchKey) {
        try {
            searchKey.createNewFile();
            doWrite(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", searchKey.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume getElement(File searchKey) {
        return null;
    }

    @Override
    protected void updateElement(Resume resume, File searchKey) {

    }

    @Override
    protected boolean isKeyExists(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected List<Resume> getListElements() {
        return null;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;
}
