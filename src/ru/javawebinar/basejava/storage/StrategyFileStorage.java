package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StrategyFileStorage extends AbstractFileStorage {
    private SerializeStrategy strategy;

    protected StrategyFileStorage(File directory, SerializeStrategy strategy) {
        super(directory);
        this.strategy = strategy;
    }

    @Override
    protected Resume readFile(InputStream stream) throws IOException {
        return strategy.read(stream);
    }

    @Override
    protected void writeFile(Resume resume, OutputStream stream) throws IOException {
        strategy.write(resume, stream);
    }

    public void setStrategy(SerializeStrategy strategy) {
        this.strategy = strategy;
    }
}
