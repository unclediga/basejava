package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {
            new File(files[i]).delete();
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        return list != null ? list.length : 0;
    }

    @Override
    protected void deleteElement(File searchKey) {
        searchKey.delete();
    }

    @Override
    protected void insertElement(Resume resume, File searchKey) {
        try {
            searchKey.createNewFile();
            writeFile(resume, searchKey);
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
        try {
            return readFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", searchKey.getName(), e);
        }
    }

    @Override
    protected void updateElement(Resume resume, File searchKey) {
        try {
            writeFile(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", searchKey.getName(), e);
        }
    }

    @Override
    protected boolean isKeyExists(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected List<Resume> getListElements() {
        List<Resume> list = new ArrayList<>();
        String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {
            try {
                list.add(getElement(new File(directory.getCanonicalPath() + File.separator + files[i])));
            } catch (IOException e) {
                throw new StorageException("IO error", files[i], e);
            }
        }
        return list;
    }

    protected abstract Resume readFile(File file) throws IOException;

    protected abstract void writeFile(Resume resume, File file) throws IOException;
}
