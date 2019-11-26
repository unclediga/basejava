package ru.javawebinar.basejava.storage.serialize;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public Resume read(InputStream stream) throws IOException {
        try (MyDataInputStream distream = new MyDataInputStream(stream)) {
            String uuid = distream.readUTF();
            String fullName = distream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = distream.readInt();
            for (int i = 0; i < size; i++) { // count of contacts
                resume.addContact(ContactType.valueOf(distream.readUTF()), distream.readUTF());
            }
            size = distream.readInt(); // count of sections
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(distream.readUTF());
                resume.addSection(sectionType, readSection(distream));
            }
            return resume;
        }
    }

    private AbstractSection readSection(MyDataInputStream distream) throws IOException {
        SectionKind sectionKind = SectionKind.valueOf(distream.readUTF());
        switch (sectionKind) {
            case LIST:
                return readListSection(distream);
            case ORGANIZATION:
                return readOrganizationSection(distream);
            case TEXT:
                return readTextSection(distream);
            default:
                throw new IllegalStateException("Undefined kind " + sectionKind);
        }
    }

    private OrganizationSection readOrganizationSection(MyDataInputStream distream) throws IOException {
        OrganizationSection section = new OrganizationSection();
        section.setTitle(distream.readUTF());
        List<OrganizationSection.Organization> organizations = new ArrayList<>();
        int sizeOrg = distream.readInt();
        for (int i = 0; i < sizeOrg; i++) {
            OrganizationSection.Organization organization = new OrganizationSection.Organization();
            OrganizationSection.Link link = new OrganizationSection.Link(distream.readUTF(), distream.readUTF());
            organization.setLink(link);
            int sizePos = distream.readInt();
            List<OrganizationSection.Position> positions = new ArrayList<>();
            for (int j = 0; j < sizePos; j++) {
                OrganizationSection.Position position = new OrganizationSection.Position();
                position.setDateFrom(LocalDate.parse(distream.readUTF()));
                position.setDateTo(LocalDate.parse(distream.readUTF()));
                position.setContent((TextSection) readSection(distream));
                positions.add(position);
            }
            organization.setPositions(positions);
            organizations.add(organization);
        }
        section.setContent(organizations);
        return section;
    }

    private ListSection readListSection(MyDataInputStream distream) throws IOException {
        ListSection section = new ListSection();
        section.setTitle(distream.readUTF());
        int size = distream.readInt();
        for (int i = 0; i < size; i++) {
            section.addSubsection(distream.readUTF());
        }
        return section;
    }

    private TextSection readTextSection(MyDataInputStream distream) throws IOException {
        TextSection textSection = new TextSection(distream.readUTF());
        textSection.setContent(distream.readUTF());
        return textSection;
    }

    @Override
    public void write(Resume resume, OutputStream stream) throws IOException {
        try (MyDataOutputStream dostream = new MyDataOutputStream(stream)) {
            dostream.writeUTF(resume.getUuid());
            dostream.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dostream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                dostream.writeUTF(contact.getKey().name());
                dostream.writeUTF(contact.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dostream.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> sectionEntry : sections.entrySet()) {
                SectionType sectionType = sectionEntry.getKey();
                dostream.writeUTF(sectionType.name());
                AbstractSection section = sectionEntry.getValue();
                if (section instanceof OrganizationSection) {
                    writeSection(dostream, (OrganizationSection) section); // SectionType.EDUCATION, SectionType.EXPERIENCE
                } else if (section instanceof ListSection) {
                    writeSection(dostream, (ListSection) section); // SectionType.ACHIEVEMENT, SectionType.QUALIFICATIONS
                } else {
                    writeSection(dostream, (TextSection) section); // SectionType.OBJECTIVE,SectionType.PERSONAL
                }
            }
        }
    }

    private void writeSection(MyDataOutputStream dostream, OrganizationSection section) throws IOException {
        dostream.writeUTF(SectionKind.ORGANIZATION.name());
        dostream.writeUTF(section.getTitle());
        List<OrganizationSection.Organization> organizations = section.getContent();
        dostream.writeInt(organizations.size());
        for (OrganizationSection.Organization organization : organizations) {
            OrganizationSection.Link link = organization.getLink();
            dostream.writeUTF(link.getTitle());
            dostream.writeUTF(link.getHomePage());
            List<OrganizationSection.Position> positions = organization.getPositions();
            dostream.writeInt(positions.size());
            for (OrganizationSection.Position position : positions) {
                dostream.writeUTF(position.getDateFrom().toString());
                dostream.writeUTF(position.getDateTo().toString());
                writeSection(dostream, position.getContent());
            }
        }
    }

    private void writeSection(MyDataOutputStream dostream, ListSection section) throws IOException {
        dostream.writeUTF(SectionKind.LIST.name());
        dostream.writeUTF(section.getTitle());
        List<String> contents = section.getContent();
        dostream.writeInt(contents.size());
        for (String content : contents) {
            dostream.writeUTF(content);
        }
    }

    private void writeSection(MyDataOutputStream dostream, TextSection section) throws IOException {
        dostream.writeUTF(SectionKind.TEXT.name());
        dostream.writeUTF(section.getTitle());
        dostream.writeUTF(section.getContent());
    }

    private static class MyDataOutputStream implements AutoCloseable {
        private DataOutputStream stream;

        MyDataOutputStream(OutputStream stream) {
            this.stream = new DataOutputStream(stream);
        }

        void writeUTF(String str) throws IOException {
            stream.writeUTF(str != null ? str : "<<null>>");
        }

        void writeInt(int val) throws IOException {
            stream.writeInt(val);
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }
    }

    private static class MyDataInputStream implements AutoCloseable {
        private DataInputStream stream;

        MyDataInputStream(InputStream stream) {
            this.stream = new DataInputStream(stream);
        }

        String readUTF() throws IOException {
            String str = stream.readUTF();
            return str.equals("<<null>>") ? null : str;
        }

        int readInt() throws IOException {
            return stream.readInt();
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }
    }

    enum SectionKind {
        TEXT,
        LIST,
        ORGANIZATION
    }
}