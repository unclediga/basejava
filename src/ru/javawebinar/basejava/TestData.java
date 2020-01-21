package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SQLStorage;

import java.time.Month;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume resume_1;
    public static final Resume resume_2;
    public static final Resume resume_3;
    public static final Resume resume_4;

    static {
        resume_1 = new Resume(UUID_1, "Full Name 1");
        resume_2 = new Resume(UUID_2, "Full Name 2");
        resume_3 = new Resume(UUID_3, "Full Name 3");
        resume_4 = new Resume(UUID_4, "Full Name 4");
        resume_1.setContact(ContactType.EMAIL, "mail1@ya.ru");
        resume_1.setContact(ContactType.TELEPHONE, "11111");
        resume_4.setContact(ContactType.TELEPHONE, "44444");
        resume_4.setContact(ContactType.SKYPE, "Skype");

        resume_1.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        resume_1.setSection(SectionType.PERSONAL, new TextSection("Personal data"));
        resume_1.setSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        resume_1.setSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        resume_1.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new OrganizationSection.Organization("Organization11", "http://Organization11.ru",
                                new OrganizationSection.Position(2005, Month.JANUARY, "position1", "content1")),
                        new OrganizationSection.Organization("Organization11", "http://Organization11.ru",
                                new OrganizationSection.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        resume_1.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new OrganizationSection.Organization("Institute", null,
                                new OrganizationSection.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null)),
                        new OrganizationSection.Organization("Institute", null,
                                new OrganizationSection.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new OrganizationSection.Organization("Organization12", "http://Organization12.ru",
                                new OrganizationSection.Position(2005, Month.FEBRUARY, "post", "IT facultet"))));
        resume_2.setContact(ContactType.SKYPE, "skype2");
        resume_2.setContact(ContactType.TELEPHONE, "22222");
        resume_2.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new OrganizationSection.Organization("Organization2", "http://Organization2.ru",
                                new OrganizationSection.Position(2015, Month.JANUARY, "position1", "content1"))));
    }


    public static void main(String[] args) {
        SQLStorage storage = (SQLStorage) Config.get().getStorage();
        storage.clear();
        storage.save(resume_1);
        storage.save(resume_2);
        storage.save(resume_3);
        storage.save(resume_4);
    }
}
