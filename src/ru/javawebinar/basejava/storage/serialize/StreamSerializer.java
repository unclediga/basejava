package ru.javawebinar.basejava.storage.serialize;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {

    Resume read(InputStream stream) throws IOException;

    void write(Resume resume, OutputStream stream) throws IOException;
}
