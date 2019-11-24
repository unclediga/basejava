package ru.javawebinar.basejava.storage.serialize;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public Resume read(InputStream stream) throws IOException {
        try (DataInputStream distream = new DataInputStream(stream)) {
            String uuid = distream.readUTF();
            String fullName = distream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = distream.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(distream.readUTF()),distream.readUTF());
            }
            return resume;
        }
    }

    @Override
    public void write(Resume resume, OutputStream stream) throws IOException {
        try (DataOutputStream dostream = new DataOutputStream(stream)) {
            dostream.writeUTF(resume.getUuid());
            dostream.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dostream.writeInt(contacts.size());
            for (Map.Entry<ContactType,String> contact:contacts.entrySet()) {
                dostream.writeUTF(contact.getKey().name());
                dostream.writeUTF(contact.getValue());
            }
        }
    }
}
