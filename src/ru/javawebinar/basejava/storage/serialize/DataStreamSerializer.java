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
        try (DataInputStream distream = new DataInputStream(stream)) {
            String uuid = distream.readUTF();
            String fullName = distream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = distream.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(distream.readUTF()), distream.readUTF());
            }

            while (distream.available() > 0) {
                SectionType sectionType = SectionType.valueOf(distream.readUTF());
                resume.addSection(sectionType, readSection(distream, sectionType));
            }
            return resume;
        }
    }

    private AbstractSection readSection(DataInputStream distream, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return readTextSection(distream);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return readListSection(distream);
            case EXPERIENCE:
            case EDUCATION:
                return readOrganizationSection(distream);
        }
        return null;
    }

    private OrganizationSection readOrganizationSection(DataInputStream distream) throws IOException {
        OrganizationSection section = new OrganizationSection(distream.readUTF());
        List<OrganizationSection.Organization> organizations = new ArrayList<>();
        int sizeOrg = distream.readInt();
        for (int i = 0; i < sizeOrg; i++) {
            OrganizationSection.Organization organization = new OrganizationSection.Organization();
            OrganizationSection.Link link = new OrganizationSection.Link(distream.readUTF(), distream.readUTF());
            organization.setLink(link);
            int sizePers = distream.readInt();
            List<OrganizationSection.Position> positions = new ArrayList<>();
            for (int j = 0; j < sizePers; j++) {
                OrganizationSection.Position position = new OrganizationSection.Position();
                position.setDateFrom(LocalDate.parse(distream.readUTF()));
                position.setDateTo(LocalDate.parse(distream.readUTF()));
                position.setContent(readTextSection(distream));
                positions.add(position);
            }
            organization.setPositions(positions);
            organizations.add(organization);
        }
        section.setContent(organizations);
        return section;
    }

    private ListSection readListSection(DataInputStream distream) throws IOException {
        ListSection section = new ListSection();
        section.setTitle(distream.readUTF());
        int size = distream.readInt();
        for (int i = 0; i < size; i++) {
            section.addSubsection(distream.readUTF());
        }
        return section;
    }

    private TextSection readTextSection(DataInputStream distream) throws IOException {
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
            writeSection(dostream, SectionType.OBJECTIVE, (TextSection) resume.getSection(SectionType.OBJECTIVE));
            writeSection(dostream, SectionType.PERSONAL, (TextSection) resume.getSection(SectionType.PERSONAL));
            writeSection(dostream, SectionType.ACHIEVEMENT, (ListSection) resume.getSection(SectionType.ACHIEVEMENT));
            writeSection(dostream, SectionType.QUALIFICATIONS, (ListSection) resume.getSection(SectionType.QUALIFICATIONS));
            writeSection(dostream, SectionType.EDUCATION, (OrganizationSection) resume.getSection(SectionType.EDUCATION));
            writeSection(dostream, SectionType.EXPERIENCE, (OrganizationSection) resume.getSection(SectionType.EXPERIENCE));
        }
    }

    private void writeSection(MyDataOutputStream dostream, SectionType sectionType, OrganizationSection section) throws IOException {
        dostream.writeUTF(sectionType.name());
        if (section == null) {
            dostream.writeUTF("");
            dostream.writeInt(0);
            return;
        }
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
                writeSection(dostream, null, position.getContent());
            }
        }
    }

    private void writeSection(MyDataOutputStream dostream, SectionType sectionType, ListSection section) throws IOException {
        dostream.writeUTF(sectionType.name());
        if (section == null) {
            dostream.writeUTF("");
            dostream.writeInt(0);
            return;
        }
        dostream.writeUTF(section.getTitle());
        List<String> contents = section.getContent();
        dostream.writeInt(contents.size());
        for (String content : contents) {
            dostream.writeUTF(content);
        }
    }

    private void writeSection(MyDataOutputStream dostream, SectionType sectionType, TextSection section) throws IOException {
        dostream.writeUTF(sectionType != null ? sectionType.name() : "INTERNAL");
        if (section == null) {
            dostream.writeUTF("");
            dostream.writeUTF("");
            return;
        }
        dostream.writeUTF(section.getTitle());
        dostream.writeUTF(section.getContent());
    }

    private class MyDataOutputStream implements AutoCloseable {
        private DataOutputStream stream;

        public MyDataOutputStream(OutputStream stream) {
            this.stream = new DataOutputStream(stream);
        }

        public void writeUTF(String str) throws IOException {
            stream.writeUTF(str != null ? str : "");
        }

        public void writeInt(int val) throws IOException {
            stream.writeInt(val);
        }

        @Override
        public void close() throws IOException {
            try (OutputStream ostream = stream) {
                stream.close();
            }
        }
    }
}