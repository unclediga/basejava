package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectStreamSerializeStrategy implements SerializeStrategy {
    @Override
    public Resume read(InputStream stream) throws IOException {
        try (ObjectInputStream oistream = new ObjectInputStream(stream)) {
            return (Resume) oistream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error reading resume", null, e);
        }
    }

    @Override
    public void write(Resume resume, OutputStream stream) throws IOException {
        try (ObjectOutputStream ostream = new ObjectOutputStream(stream)) {
            ostream.writeObject(resume);
        }
    }
}
