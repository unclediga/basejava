package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.time.Month;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected Storage storage;
    protected static final File STORAGE_DIR = new File("storage");

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume resume_1;
    private static final Resume resume_2;
    private static final Resume resume_3;
    private static final Resume resume_4;

    static {
        resume_1 = new Resume(UUID_1, "Full Name 1");
        resume_2 = new Resume(UUID_2, "Full Name 2");
        resume_3 = new Resume(UUID_3, "Full Name 3");
        resume_4 = new Resume(UUID_4, "Full Name 4");
        resume_1.addContact(ContactType.EMAIL, "mail1@ya.ru");
        resume_1.addContact(ContactType.TELEPHONE, "11111");
        resume_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        resume_1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        resume_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        resume_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        resume_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection("EXPERIENCE",
                        new OrganizationSection.Organization("Organization11", "http://Organization11.ru",
                                new OrganizationSection.Position(2005, Month.JANUARY, "position1", "content1")),
                        new OrganizationSection.Organization("Organization11", "http://Organization11.ru",
                                new OrganizationSection.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        resume_1.addSection(SectionType.EDUCATION,
                new OrganizationSection("EDUCATION",
                        new OrganizationSection.Organization("Institute", null,
                                new OrganizationSection.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null)),
                        new OrganizationSection.Organization("Institute", null,
                                new OrganizationSection.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new OrganizationSection.Organization("Organization12", "http://Organization12.ru",
                                new OrganizationSection.Position(2005, Month.FEBRUARY, "post", "IT facultet"))));
        resume_2.addContact(ContactType.SKYPE, "skype2");
        resume_2.addContact(ContactType.TELEPHONE, "22222");
        resume_2.addSection(SectionType.EXPERIENCE,
                new OrganizationSection("EXPERIENCE",
                        new OrganizationSection.Organization("Organization2", "http://Organization2.ru",
                                new OrganizationSection.Position(2015, Month.JANUARY, "position1", "content1"))));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume_1);
        storage.save(resume_2);
        storage.save(resume_3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2, "Full Name2");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(resume_4);
    }

    @Test
    public void getAllSorted() throws Exception {
        assertEquals(Arrays.asList(resume_1, resume_2, resume_3), storage.getAllSorted());
    }

    @Test
    public void save() throws Exception {
        storage.save(resume_4);
        assertSize(4);
        assertGet(resume_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertGet(resume_1);
        assertGet(resume_2);
        assertGet(resume_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    private void assertSize(int i) {
        assertEquals(i, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}