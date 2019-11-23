package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialize.StreamSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    protected StreamSerializer serializer;
    private Path directory;

    protected PathStorage(String dir, StreamSerializer serializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not readable/writable");
        }
        this.serializer = serializer;
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::deleteElement);
    }

    @Override
    public int size() {
        return Math.toIntExact(getFilesList().count());
    }

    @Override
    protected void deleteElement(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("File delete error" + searchKey.toAbsolutePath(), e);
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
            return serializer.read(Files.newInputStream(searchKey));
        } catch (IOException e) {
            throw new StorageException("File read error " + searchKey.toAbsolutePath(), e);
        }
    }

    @Override
    protected void updateElement(Resume resume, Path searchKey) {
        try {
            serializer.write(resume, Files.newOutputStream(searchKey));
        } catch (IOException e) {
            throw new StorageException("File write error " + searchKey.toAbsolutePath(), e);
        }
    }

    @Override
    protected boolean isKeyExists(Path searchKey) {
        return Files.isRegularFile(searchKey);
    }

    @Override
    protected List<Resume> getListElements() {
        return getFilesList().map(this::getElement).collect(Collectors.toList());
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }
}
