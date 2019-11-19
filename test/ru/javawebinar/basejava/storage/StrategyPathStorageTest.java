package ru.javawebinar.basejava.storage;

public class StrategyPathStorageTest extends AbstractStorageTest {

    public StrategyPathStorageTest() {
        super(new StrategyPathStorage(STORAGE_DIR.getName(), new ObjectStreamSerializeStrategy()));
    }
}
