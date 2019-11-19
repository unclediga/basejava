package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializeStrategy {

    Resume read(InputStream stream) throws IOException;

    void write(Resume resume, OutputStream stream) throws IOException;
}
