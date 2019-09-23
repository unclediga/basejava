package ru.javawebinar.basejava.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final Map<ContactType, Contact> contacts = new EnumMap(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, Contact> getContacts() {
        return contacts;
    }

    public Contact getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setContact(Contact contact) {
        contacts.put(contact.getType(), contact);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public Section getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void setSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

    private Section getExistOrCreateSection(SectionType sectionType) {
        Section section = getSection(sectionType);
        if (section == null) {
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    section = new TextSection(sectionType.getTitle());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    section = new ListSection(sectionType.getTitle());
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    section = new OrganizationSection(sectionType.getTitle());
                    break;
            }
            setSection(sectionType, section);
        }
        return section;
    }

    public void addAchievement(String content) {
        ListSection section = (ListSection) getExistOrCreateSection(SectionType.ACHIEVEMENT);
        section.addSubsection(content);
    }

    public void addObjective(String content) {
        TextSection section = (TextSection) getExistOrCreateSection(SectionType.OBJECTIVE);
        section.setContent(content);
    }

    public void addPersonal(String content) {
        TextSection section = (TextSection) getExistOrCreateSection(SectionType.PERSONAL);
        section.setContent(content);
    }

    public void addQualification(String content) {
        ListSection section = (ListSection) getExistOrCreateSection(SectionType.QUALIFICATIONS);
        section.addSubsection(content);
    }

    public void addEducation(String organizationTitle, String organizationLink,
                             int monthFrom, int yearFrom, int monthTo, int yearTo, String workTitle) {
        OrganizationSection section = (OrganizationSection) getExistOrCreateSection(SectionType.EDUCATION);
        section.addSubsection(organizationTitle, organizationLink, yearFrom, monthFrom, yearTo, monthTo, workTitle, null);
    }

    public void addExperience(String organizationTitle, String organizationLink,
                              int monthFrom, int yearFrom, int monthTo, int yearTo, String workTitle, String content) {
        OrganizationSection section = (OrganizationSection) getExistOrCreateSection(SectionType.EXPERIENCE);
        section.addSubsection(organizationTitle, organizationLink, yearFrom, monthFrom, yearTo, monthTo, workTitle, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return 31 * uuid.hashCode() + (fullName == null ? 0 : fullName.hashCode());
    }

    @Override
    public String toString() {
        return uuid + " : " + fullName + "\n" +
                contacts.toString() + "\n" +
                sections.toString();
    }

    @Override
    public int compareTo(Resume o) {
        int res = fullName.compareTo(o.getFullName());
        return res != 0 ? res : uuid.compareTo(o.getUuid());
    }
}