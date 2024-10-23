package ua.foxminded.moldavets.project.storage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.moldavets.project.exception.ExistStorageException;
import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.storage.serializer.ObjectStreamSerializer;

import java.io.File;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class FileStorageImplTest {

    File directory = new File("C:\\Users\\steam\\IdeaProjects\\Java-SE-app\\storage\\testStorage");
    boolean testDirectory = directory.mkdir();
    private final FileStorage<File> fileStorage = new FileStorageImpl(directory, new ObjectStreamSerializer());

    Resume resume1 = new Resume("uuid1", "John Doe");
    Resume resume2 = new Resume("uuid2", "Ethan Parker");
    Resume resume3 = new Resume("uuid3", "Olivia Bennett");

    @BeforeEach
    void setUp() {
        fileStorage.save(resume1, fileStorage.getSearchKey(resume1.getUuid()));
        fileStorage.save(resume2, fileStorage.getSearchKey(resume2.getUuid()));
        fileStorage.save(resume3, fileStorage.getSearchKey(resume3.getUuid()));
    }

    @AfterEach
    void tearDown() {
        File toBeDeleted = new File("C:\\Users\\steam\\IdeaProjects\\Java-SE-app\\storage\\testStorage");
        deleteDirectory(toBeDeleted);
    }

    @Test
    void save_shouldReturnException_whenInputAlreadyContainsExistResumeInStorage() {
        Exception actual = assertThrows(ExistStorageException.class,
                () -> fileStorage.save(resume1, fileStorage.getSearchKey(resume1.getUuid())));
        assertEquals("Resume " + resume1.getUuid() + " already exist", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputResumeIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> fileStorage.save(null, null));
        assertEquals("Resume or search key cannot be null", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputSearchKeyIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> fileStorage.save(resume1, null));
        assertEquals("Resume or search key cannot be null", actual.getMessage());
    }




    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }
}