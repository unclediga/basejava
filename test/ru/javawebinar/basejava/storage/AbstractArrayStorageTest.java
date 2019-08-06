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

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
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
        final Resume resume = new Resume(UUID_4);
        storage.update(resume);
    }

    @Test
    public void getAll() throws Exception {

        final Resume[] actualStorage = storage.getAll();
        final Resume[] expectedStorage = new Resume[]{new Resume(UUID_1), new Resume(UUID_2),new Resume(UUID_3)};
        Arrays.sort(actualStorage);
        Assert.assertArrayEquals(expectedStorage, actualStorage);
    }

    @Test
    public void save() throws Exception {
        final Resume resume = new Resume(UUID_4);
        storage.save(resume);
        Assert.assertEquals(UUID_4, storage.get(UUID_4).getUuid());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        final Resume resume = new Resume(UUID_2);
        storage.save(resume);
    }

    @Test(expected = StorageException.class)
    public void saveWithOverflow() throws Exception {
        try {
            for (int i = storage.size() + 1; i <= 10_000; i++) {
                storage.save(new Resume("uuid"+i));
            }
        }catch (NotExistStorageException|ExistStorageException e){
            Assert.fail("Unexpected exception type. Expected: 'Overflow', actual:'"+ e.getMessage()+"'");
        }catch (StorageException se){
            Assert.fail("Get 'Overflow exception' when storage is not full");
        }

        try{
            storage.save(new Resume("uuid10001"));
        }catch (NotExistStorageException|ExistStorageException e){
            Assert.fail("Unexpected exception type. Expected: 'Overflow', actual:'"+ e.getMessage()+"'");
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        storage.get(UUID_2);
    }

    @Test
    public void get() throws Exception {
        Resume resume = storage.get(UUID_2);
        Assert.assertTrue(resume.getUuid().equals(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}