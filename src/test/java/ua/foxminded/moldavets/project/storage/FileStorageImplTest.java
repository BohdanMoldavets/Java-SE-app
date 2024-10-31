package ua.foxminded.moldavets.project.storage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.moldavets.project.exception.ExistStorageException;
import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
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
        if (toBeDeleted.listFiles() != null) {
            deleteDirectory(toBeDeleted);
        }
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

    @Test
    void save_shouldReturnException_whenInputSearchKeyAndResumeIsEmpty() {
        File file = new File("");
        Resume resume = new Resume("","");
        Exception actual = assertThrows(StorageException.class,
                () -> fileStorage.save(resume, file));
        assertEquals("Couldn't save resume " + resume.getUuid() + "to " +
                file.getAbsolutePath() + "\\" + resume.getUuid(),
                actual.getMessage());
    }

    @Test
    void update_shouldReturnException_whenInputResumeDoesNotExist() {
        Resume resume = new Resume("testUuid1", "testFullName");
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> fileStorage.update(resume, fileStorage.getSearchKey(resume.getUuid())));
        assertEquals("Resume " + resume.getUuid() + " does not exist", actual.getMessage());
    }

    @Test
    void update_shouldUpdateResume_whenInputResumeExists() {
        Resume resume4 = new Resume("uuid1","Shara Doe");
        fileStorage.update(resume4, fileStorage.getSearchKey(resume4.getUuid()));

        assertEquals("Shara Doe", fileStorage.get(fileStorage.getSearchKey(resume1.getUuid())).getFullName());
    }

    @Test
    void update_shouldReturnException_whenInputSearchKeyIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> fileStorage.update(resume1, null));
        assertEquals("Resume or search key cannot be null", actual.getMessage());
    }

    @Test
    void update_shouldReturnException_whenInputResumeIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> fileStorage.save(null, null));
        assertEquals("Resume or search key cannot be null", actual.getMessage());
    }

    @Test
    void delete_shouldReturnException_whenInputResumeDoesNotExist() {
        Resume resume = new Resume("testUuid1", "testFullName");
        Exception actual = assertThrows(StorageException.class,
                () -> fileStorage.delete(fileStorage.getSearchKey(resume.getUuid())));
        assertEquals("File delete exception", actual.getMessage());
    }

    @Test
    void delete_shouldDeleteResume_whenInputResumeExists() {
        fileStorage.delete(fileStorage.getSearchKey(resume1.getUuid()));
        assertFalse(fileStorage.isExist(fileStorage.getSearchKey(resume1.getUuid())));
    }

    @Test
    void delete_shouldReturnException_whenInputSearchKeyIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> fileStorage.delete( null));
        assertEquals("File cannot be null", actual.getMessage());
    }

    @Test
    void delete_shouldReturnException_whenInputSearchKeyIsEmpty() {
        Exception actual = assertThrows(StorageException.class,
                () -> fileStorage.delete(new File("")));
        assertEquals("File delete exception", actual.getMessage());
    }

    @Test
    void clear_shouldReturnException_whenStorageIsEmpty() {
        deleteDirectory(new File("C:\\Users\\steam\\IdeaProjects\\Java-SE-app\\storage\\testStorage"));
        Exception actual = assertThrows(StorageException.class,
                fileStorage::clear);
        assertEquals("Storage is empty", actual.getMessage());
    }

    @Test
    void clear_shouldClearStorage_whenStorageIsNotEmpty() {
        fileStorage.clear();
        assertEquals(directory.listFiles().length, 0);
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