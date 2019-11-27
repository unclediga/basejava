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
                AbstractSection section = null;
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
                position.setDateFrom(LocalDate.parse(readUTF(distream)));
                position.setDateTo(LocalDate.parse(readUTF(distream)));
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
        return new TextSection(distream.readUTF());
    }

    @Override
    public void write(Resume resume, OutputStream stream) throws IOException {
        try (DataOutputStream dostream = new DataOutputStream(stream)) {
            writeUTF(dostream, resume.getUuid());
            writeUTF(dostream, resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dostream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                writeUTF(dostream, contact.getKey().name());
                writeUTF(dostream, contact.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dostream.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> sectionEntry : sections.entrySet()) {
                SectionType sectionType = sectionEntry.getKey();
                writeUTF(dostream, sectionType.name());
                AbstractSection section = sectionEntry.getValue();
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
            }
        }
    }

    private void writeSection(DataOutputStream dostream, OrganizationSection section) throws IOException {
        List<OrganizationSection.Organization> organizations = section.getContent();
        dostream.writeInt(organizations.size());
        for (OrganizationSection.Organization organization : organizations) {
            writeElement(dostream, organization);
        }
    }

    private void writeElement(DataOutputStream dostream, OrganizationSection.Organization organization) throws IOException {
        OrganizationSection.Link link = organization.getLink();
        writeUTF(dostream, link.getTitle());
        writeUTF(dostream, link.getHomePage());
        List<OrganizationSection.Position> positions = organization.getPositions();
        writeList(dostream, positions);
    }

    private void writeList(DataOutputStream dostream, List<OrganizationSection.Organization> list) throws IOException {
        dostream.writeInt(list.size());
        for (OrganizationSection.Organization el : list) {
            writeElement(dostream, el);
        }
    }

    private void writeList(DataOutputStream dostream, List<OrganizationSection.Position> list) throws IOException {
        dostream.writeInt(list.size());
        for (OrganizationSection.Position el : list) {
            writeElement(dostream, el);
        }
    }

    private void writeElement(DataOutputStream dostream, OrganizationSection.Position position) throws IOException {
        writeUTF(dostream, position.getDateFrom().toString());
        writeUTF(dostream, position.getDateTo().toString());
        writeUTF(dostream, position.getTitle());
        writeUTF(dostream, position.getDescription());
    }

    private void writeSection(DataOutputStream dostream, ListSection section) throws IOException {
        List<String> contents = section.getContent();
        dostream.writeInt(contents.size());
        for (String content : contents) {
            writeUTF(dostream, content);
        }
    }

    private void writeSection(DataOutputStream dostream, TextSection section) throws IOException {
        writeUTF(dostream, section.getContent());
    }

    private String readUTF(DataInputStream stream) throws IOException {
        String str = stream.readUTF();
        return str.equals("") ? null : str;
    }

    private void writeUTF(DataOutputStream stream, String str) throws IOException {
        stream.writeUTF(str != null ? str : "");
    }
}