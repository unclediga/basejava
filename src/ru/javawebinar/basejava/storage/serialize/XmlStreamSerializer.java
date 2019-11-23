package ru.javawebinar.basejava.storage.serialize;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.OrganizationSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser parser;

    public XmlStreamSerializer() {
        this.parser = new XmlParser(Resume.class,
                OrganizationSection.class,
                ListSection.class,
                TextSection.class,
                OrganizationSection.Organization.class,
                OrganizationSection.Position.class,
                OrganizationSection.Link.class);
    }

    @Override
    public Resume read(InputStream stream) throws IOException {
        try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return (Resume) parser.unmarshall(reader);
        }
    }

    @Override
    public void write(Resume resume, OutputStream stream) throws IOException {
        try (Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
            parser.marshall(resume, writer);
        }
    }
}
