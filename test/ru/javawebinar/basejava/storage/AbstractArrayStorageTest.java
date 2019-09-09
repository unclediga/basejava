package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveWithOverflow() throws Exception {
        try {
            for (int i = storage.size(); i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + (i + 1), "Full Name"));
            }
        } catch (StorageException se) {
            Assert.fail("Get 'Overflow exception' when storage is not full");
        }
        storage.save(new Resume("uuid10001", "Full Name"));
    }
}