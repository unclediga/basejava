package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(Config.get().getStorage());
    }
}
