package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not readable/writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteElement);
        } catch (IOException e) {
            throw new StorageException("Error delete " + directory.toAbsolutePath(), null, e);
        }
    }

    @Override
    public int size() {
        try {
            return Math.toIntExact(Files.list(directory).count());
        } catch (IOException e) {
            throw new StorageException("Directory read error " + directory.toAbsolutePath(), null, e);
        }
    }

    @Override
    protected void deleteElement(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("File delete error" + searchKey.toAbsolutePath(), null, e);
        }
    }

    @Override
    protected void insertElement(Resume resume, Path searchKey) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + searchKey.toAbsolutePath(), resume.getUuid(), e);
        }
        updateElement(resume, searchKey);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected Resume getElement(Path searchKey) {
        try {
            return readFile(Files.newInputStream(searchKey));
        } catch (IOException e) {
            throw new StorageException("File read error " + searchKey.toAbsolutePath(), null, e);
        }
    }

    @Override
    protected void updateElement(Resume resume, Path searchKey) {
        try {
            writeFile(resume, Files.newOutputStream(searchKey));
        } catch (IOException e) {
            throw new StorageException("File write error " + searchKey.toAbsolutePath(), null, e);
        }
    }

    @Override
    protected boolean isKeyExists(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected List<Resume> getListElements() {
        List<Resume> list = new ArrayList<>();
        try {
            Files.list(directory).forEach(f -> list.add(getElement(f)));
        } catch (IOException e) {
            throw new StorageException("Directory read error " + directory.toAbsolutePath(), null, e);
        }
        return list;
    }

    protected abstract Resume readFile(InputStream stream) throws IOException;

    protected abstract void writeFile(Resume resume, OutputStream stream) throws IOException;
}
