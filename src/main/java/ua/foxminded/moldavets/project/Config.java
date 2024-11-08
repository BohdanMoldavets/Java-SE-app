package ua.foxminded.moldavets.project;

import ua.foxminded.moldavets.project.storage.SqlStorage;
import ua.foxminded.moldavets.project.storage.Storage;

import java.io.*;
import java.util.Properties;

public class Config {

    protected static final File PROPS = new File("C:\\Users\\steam\\IdeaProjects\\Java-SE-app\\config\\resumes.properties");
    private static final Config INSTANCE = new Config();

    private final Properties properties = new Properties();
    private final File storageDir;
    private final Storage storage;

    private Config() {
        try(FileInputStream inputStream = new FileInputStream(PROPS)) {
            properties.load(inputStream);
            storageDir = new File(getProperties("storage.dir"));
            storage = new SqlStorage(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid file");
        }

    }

    public static Config get() {return INSTANCE;}

    public Storage getStorage() {return storage;}

    public File getStorageDir() {
        return storageDir;
    }

    public String getProperties(String property) {
        try(FileInputStream inputStream = new FileInputStream(PROPS)) {
            properties.load(inputStream);
            return properties.getProperty(property);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid file");
        }
    }
}
