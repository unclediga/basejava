package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
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
        File[] files = directory.listFiles();
        if (files == null) return;
        for (File file : files) {
            deleteElement(file);
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    @Override
    protected void deleteElement(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("File delete error", searchKey.getName());
        }
    }

    @Override
    protected void insertElement(Resume resume, File searchKey) {
        try {
            searchKey.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + searchKey.getAbsolutePath(), searchKey.getName(), e);
        }
        updateElement(resume, searchKey);
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume getElement(File searchKey) {
        try {
            return readFile(new FileInputStream(searchKey));
        } catch (IOException e) {
            throw new StorageException("File read error", searchKey.getName(), e);
        }
    }

    @Override
    protected void updateElement(Resume resume, File searchKey) {
        try {
            writeFile(resume, new FileOutputStream(searchKey));
        } catch (IOException e) {
            throw new StorageException("File write error", searchKey.getName(), e);
        }
    }

    @Override
    protected boolean isKeyExists(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected List<Resume> getListElements() {
        List<Resume> list = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) return list;
        for (File file : files) {
            list.add(getElement(file));
        }
        return list;
    }

    protected abstract Resume readFile(InputStream stream) throws IOException;

    protected abstract void writeFile(Resume resume, OutputStream stream) throws IOException;
}
