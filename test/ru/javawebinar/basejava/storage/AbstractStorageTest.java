package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.TestData.*;

public abstract class AbstractStorageTest {
    protected Storage storage;
    protected static final File STORAGE_DIR = Config.get().getStorageDir();


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
        resume_2.setContact(ContactType.EMAIL, "mail1@google.com");
        resume_2.setContact(ContactType.SKYPE, "NewSkype");
        resume_2.setContact(ContactType.TELEPHONE, "+7 921 222-22-22");
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