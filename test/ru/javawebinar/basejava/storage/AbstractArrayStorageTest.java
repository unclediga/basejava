package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume resume_1 = new Resume(UUID_1);
    private static final Resume resume_2 = new Resume(UUID_2);
    private static final Resume resume_3 = new Resume(UUID_3);
    private static final Resume resume_4 = new Resume(UUID_4);


    public AbstractArrayStorageTest(Storage storage) {
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
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2);
        storage.update(resume);
        Assert.assertSame(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(resume_4);
    }

    @Test
    public void getAll() throws Exception {
        final Resume[] actualStorage = storage.getAll();
        final Resume[] expectedStorage = new Resume[]{resume_1, resume_2, resume_3};
        Arrays.sort(actualStorage);
        Assert.assertArrayEquals(expectedStorage, actualStorage);
    }

    @Test
    public void save() throws Exception {
        storage.save(resume_4);
        Assert.assertEquals(resume_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume_2);
    }

    @Test(expected = StorageException.class)
    public void saveWithOverflow() throws Exception {
        try {
            for (int i = storage.size() + 1; i <= 10_000; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (NotExistStorageException | ExistStorageException e) {
            Assert.fail("Unexpected exception type. Expected: 'Overflow', actual:'" + e.getMessage() + "'");
        } catch (StorageException se) {
            Assert.fail("Get 'Overflow exception' when storage is not full");
        }
        storage.save(new Resume("uuid10001"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        storage.get(UUID_2);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(resume_2,storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}