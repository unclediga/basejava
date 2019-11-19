package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String directory) {
        super(directory);
    }

    @Override
    protected Resume readFile(InputStream stream) throws IOException {
        try (ObjectInputStream oistream = new ObjectInputStream(stream)) {
            return (Resume) oistream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error reading resume", null, e);
        }
    }

    @Override
    protected void writeFile(Resume resume, OutputStream stream) throws IOException {
        try (ObjectOutputStream ostream = new ObjectOutputStream(stream)) {
            ostream.writeObject(resume);
        }
    }
}
