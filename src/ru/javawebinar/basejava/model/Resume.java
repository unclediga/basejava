package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.UUID;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final HashMap<ContactType, Contact> contacts = new HashMap<>();
    private final HashMap<SectionType, Section> sections = new HashMap<>();

    public void addContact(ContactType contactType, String title, String content) {
        contacts.put(contactType, new Contact(contactType, title, content));
    }

    public void addSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

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
                contacts.toString() +"\n"+
                sections.toString();
    }

    @Override
    public int compareTo(Resume o) {
        int res = fullName.compareTo(o.getFullName());
        return res != 0 ? res : uuid.compareTo(o.getUuid());
    }

    public void addAchievement(String content) {
        ListSection section = (ListSection) sections.get(SectionType.ACHIEVEMENT);
        if (section == null) {
            section = new ListSection(SectionType.ACHIEVEMENT.getTitle());
            sections.put(SectionType.ACHIEVEMENT, section);
        }
        section.addSubsection(content);
    }

    public void addObjective(String content) {
        sections.put(SectionType.OBJECTIVE, new TextSection(SectionType.OBJECTIVE.getTitle(), content));
    }

    public void addPersonal(String content) {
        sections.put(SectionType.PERSONAL, new TextSection(SectionType.PERSONAL.getTitle(), content));
    }

    public void addQualification(String content) {
        ListSection section = (ListSection) sections.get(SectionType.QUALIFICATIONS);
        if (section == null) {
            section = new ListSection(SectionType.QUALIFICATIONS.getTitle());
            sections.put(SectionType.QUALIFICATIONS, section);
        }
        section.addSubsection(content);
    }

    public void addEducation(String organizationTitle, String organizationLink, String dateFrom, String dateTo, String workTitle) {
        OrganizationSection section = (OrganizationSection) sections.get(SectionType.EDUCATION);
        if (section == null) {
            section = new OrganizationSection(SectionType.EDUCATION.getTitle());
            sections.put(SectionType.EDUCATION, section);
        }
        section.addSubsection(organizationTitle, organizationLink, dateFrom, dateTo, workTitle, null);
    }

    public void addExperience(String organizationTitle, String organizationLink, String dateFrom, String dateTo, String workTitle, String content) {
        OrganizationSection section = (OrganizationSection) sections.get(SectionType.EDUCATION);
        if (section == null) {
            section = new OrganizationSection(SectionType.EDUCATION.getTitle());
            sections.put(SectionType.EDUCATION, section);
        }
        section.addSubsection(organizationTitle, organizationLink, dateFrom, dateTo, workTitle, content);
    }
}