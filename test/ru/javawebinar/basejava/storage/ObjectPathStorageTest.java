package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialize.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getName(), new ObjectStreamSerializer()));
    }
}
