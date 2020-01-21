package ru.javawebinar.basejava.storage.serialize;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public Resume read(InputStream stream) throws IOException {
        try (DataInputStream distream = new DataInputStream(stream)) {
            String uuid = readUTF(distream);
            String fullName = readUTF(distream);
            Resume resume = new Resume(uuid, fullName);
            readSequence(distream, () -> resume.setContact(ContactType.valueOf(readUTF(distream)), readUTF(distream)));
            readSequence(distream, () -> {
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
                resume.setSection(sectionType, section);
            });
            return resume;
        }
    }

    interface SequenceReader {
        void read() throws IOException;
    }

    private void readSequence(DataInputStream istream, SequenceReader reader) throws IOException {
        int size = istream.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private OrganizationSection readOrganizationSection(DataInputStream distream) throws IOException {
        OrganizationSection section = new OrganizationSection();
        List<OrganizationSection.Organization> organizations = new ArrayList<>();
        readSequence(distream, () -> {
            OrganizationSection.Organization organization = new OrganizationSection.Organization();
            OrganizationSection.Link link = new OrganizationSection.Link(readUTF(distream), readUTF(distream));
            organization.setLink(link);
            List<OrganizationSection.Position> positions = new ArrayList<>();
            readSequence(distream, () -> {
                OrganizationSection.Position position = new OrganizationSection.Position();
                position.setDateFrom(LocalDate.parse(distream.readUTF()));
                position.setDateTo(LocalDate.parse(distream.readUTF()));
                position.setTitle(readUTF(distream));
                position.setDescription(readUTF(distream));
                positions.add(position);
            });
            organization.setPositions(positions);
            organizations.add(organization);
        });
        section.setContent(organizations);
        return section;
    }

    private ListSection readListSection(DataInputStream distream) throws IOException {
        ListSection section = new ListSection();
        readSequence(distream, () -> section.addSubsection(readUTF(distream)));
        return section;
    }

    private TextSection readTextSection(DataInputStream distream) throws IOException {
        return new TextSection(readUTF(distream));
    }

    private String readUTF(DataInputStream stream) throws IOException {
        String str = stream.readUTF();
        return str.equals("") ? null : str;
    }

    @Override
    public void write(Resume resume, OutputStream stream) throws IOException {
        try (DataOutputStream dostream = new DataOutputStream(stream)) {
            writeUTF(dostream, resume.getUuid());
            writeUTF(dostream, resume.getFullName());
            writeCollection(dostream, resume.getContacts().entrySet(), contactEntry -> {
                writeUTF(dostream, contactEntry.getKey().name());
                writeUTF(dostream, contactEntry.getValue());
            });
            writeCollection(dostream, resume.getSections().entrySet(), sectionEntry -> {
                SectionType sectionType = sectionEntry.getKey();
                AbstractSection section = sectionEntry.getValue();
                writeUTF(dostream, sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writeSection(dostream, (TextSection) section);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeSection(dostream, (ListSection) section);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeSection(dostream, (OrganizationSection) section);
                        break;
                    default:
                        throw new IllegalStateException("Unknown section type " + sectionType);
                }
            });
        }
    }

    interface ElementWriter<T> {
        void write(T element) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream stream, Collection<T> collection, ElementWriter<T> elementWriter) throws IOException {
        stream.writeInt(collection.size());
        for (T el : collection) {
            elementWriter.write(el);
        }
    }

    private void writeSection(DataOutputStream stream, TextSection section) throws IOException {
        writeUTF(stream, section.getContent());
    }

    private void writeSection(DataOutputStream stream, ListSection section) throws IOException {
        writeCollection(stream, section.getContent(), element -> writeUTF(stream, element));
    }

    private void writeSection(DataOutputStream stream, OrganizationSection section) throws IOException {
        writeCollection(stream, section.getContent(), organization -> {
            OrganizationSection.Link link = organization.getLink();
            writeUTF(stream, link.getTitle());
            writeUTF(stream, link.getHomePage());
            writeCollection(stream, organization.getPositions(), position -> {
                writeUTF(stream, position.getDateFrom().toString());
                writeUTF(stream, position.getDateTo().toString());
                writeUTF(stream, position.getTitle());
                writeUTF(stream, position.getDescription());
            });
        });
    }

    private void writeUTF(DataOutputStream stream, String str) throws IOException {
        stream.writeUTF(str != null ? str : "");
    }
}
