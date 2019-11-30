package ru.javawebinar.basejava.storage.serialize;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public Resume read(InputStream stream) throws IOException {
        try (DataInputStream distream = new DataInputStream(stream)) {
            String uuid = readUTF(distream);
            String fullName = readUTF(distream);
            Resume resume = new Resume(uuid, fullName);
            int size = distream.readInt();
            for (int i = 0; i < size; i++) { // count of contacts
                resume.addContact(ContactType.valueOf(readUTF(distream)), readUTF(distream));
            }
            size = distream.readInt(); // count of sections
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(readUTF(distream));
                AbstractSection section;
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = readTextSection(distream);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        section = readListSection(distream);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        section = readOrganizationSection(distream);
                        break;
                    default:
                        throw new IllegalStateException("Unknown section type " + sectionType);
                }
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }

    private OrganizationSection readOrganizationSection(DataInputStream distream) throws IOException {
        OrganizationSection section = new OrganizationSection();
        List<OrganizationSection.Organization> organizations = new ArrayList<>();
        int sizeOrg = distream.readInt();
        for (int i = 0; i < sizeOrg; i++) {
            OrganizationSection.Organization organization = new OrganizationSection.Organization();
            OrganizationSection.Link link = new OrganizationSection.Link(readUTF(distream), readUTF(distream));
            organization.setLink(link);
            int sizePos = distream.readInt();
            List<OrganizationSection.Position> positions = new ArrayList<>();
            for (int j = 0; j < sizePos; j++) {
                OrganizationSection.Position position = new OrganizationSection.Position();
                position.setDateFrom(LocalDate.parse(distream.readUTF()));
                position.setDateTo(LocalDate.parse(distream.readUTF()));
                position.setTitle(readUTF(distream));
                position.setDescription(readUTF(distream));
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
        int size = distream.readInt();
        for (int i = 0; i < size; i++) {
            section.addSubsection(readUTF(distream));
        }
        return section;
    }

    private TextSection readTextSection(DataInputStream distream) throws IOException {
        return new TextSection(readUTF(distream));
    }

    interface ElementWriter<T> {
        void write(DataOutputStream stream, T element) throws IOException;
    }

    @Override
    public void write(Resume resume, OutputStream stream) throws IOException {
        try (DataOutputStream dostream = new DataOutputStream(stream)) {
            writeUTF(dostream, resume.getUuid());
            writeUTF(dostream, resume.getFullName());
            writeList(dostream, resume.getContacts().entrySet(), (cstream, contactEntry) -> {
                writeUTF(cstream, contactEntry.getKey().name());
                writeUTF(cstream, contactEntry.getValue());
            });
            writeList(dostream, resume.getSections().entrySet(), (sstream, sectionEntry) -> {
                SectionType sectionType = sectionEntry.getKey();
                AbstractSection section = sectionEntry.getValue();
                writeUTF(sstream, sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writeSection(sstream, (TextSection) section);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeSection(sstream, (ListSection) section);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeSection(sstream, (OrganizationSection) section);
                        break;
                    default:
                        throw new IllegalStateException("Unknown section type " + sectionType);
                }
            });
        }
    }

    private <T> void writeList(DataOutputStream stream, Collection<T> collection, ElementWriter<T> elementWriter) throws IOException {
        stream.writeInt(collection.size());
        for (T el : collection) {
            elementWriter.write(stream, el);
        }
    }

    private void writeSection(DataOutputStream stream, TextSection section) throws IOException {
        writeUTF(stream, section.getContent());
    }

    private void writeSection(DataOutputStream stream, ListSection section) throws IOException {
        writeList(stream, section.getContent(), this::writeUTF);
    }

    private void writeSection(DataOutputStream stream, OrganizationSection section) throws IOException {
        writeList(stream, section.getContent(), (ostream, organization) -> {
            OrganizationSection.Link link = organization.getLink();
            writeUTF(ostream, link.getTitle());
            writeUTF(ostream, link.getHomePage());
            writeList(ostream, organization.getPositions(), (pstream, position) -> {
                writeUTF(pstream, position.getDateFrom().toString());
                writeUTF(pstream, position.getDateTo().toString());
                writeUTF(pstream, position.getTitle());
                writeUTF(pstream, position.getDescription());
            });
        });
    }

    private String readUTF(DataInputStream stream) throws IOException {
        String str = stream.readUTF();
        return str.equals("") ? null : str;
    }

    private void writeUTF(DataOutputStream stream, String str) throws IOException {
        stream.writeUTF(str != null ? str : "");
    }
}
