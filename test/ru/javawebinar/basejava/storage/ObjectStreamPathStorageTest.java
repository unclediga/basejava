package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialize.ObjectStreamSerializeStrategy;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getName(), new ObjectStreamSerializeStrategy()));
    }
}
