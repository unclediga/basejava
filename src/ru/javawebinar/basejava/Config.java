package ru.javawebinar.basejava;

import ru.javawebinar.basejava.storage.SQLStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static File PROPS = new File("config\\resumes.properties");
    private static Config INSTANCE;
    private final File storageDir;
    private final Storage storage;


    public static Config get() {
        if (INSTANCE == null)
            INSTANCE = new Config();
        return INSTANCE;
    }

    public static Config get(String path) {
        PROPS = new File(path);
        return get();
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SQLStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
