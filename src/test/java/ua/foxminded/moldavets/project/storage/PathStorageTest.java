package ua.foxminded.moldavets.project.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.moldavets.project.exception.ExistStorageException;
import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.storage.serializer.ObjectStreamSerializer;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PathStorageTest {

    String directory = "C:\\Users\\steam\\IdeaProjects\\Java-SE-app\\storage\\testStorage";
    boolean testDirectory = new File(directory).mkdir();
    private final FileStorage<Path> pathStorage = new PathStorage(directory, new ObjectStreamSerializer());

    Resume resume1 = new Resume("uuid1", "John Doe");
    Resume resume2 = new Resume("uuid2", "Ethan Parker");
    Resume resume3 = new Resume("uuid3", "Olivia Bennett");

    @BeforeEach
    void setUp() {
        pathStorage.save(resume1, pathStorage.getSearchKey(resume1.getUuid()));
        pathStorage.save(resume2, pathStorage.getSearchKey(resume2.getUuid()));
        pathStorage.save(resume3, pathStorage.getSearchKey(resume3.getUuid()));
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
                () -> pathStorage.save(resume1, pathStorage.getSearchKey(resume1.getUuid())));
        assertEquals("Resume " + resume1.getUuid() + " already exist", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputResumeIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> pathStorage.save(null, null));
        assertEquals("Resume or search key cannot be null", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputSearchKeyIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> pathStorage.save(resume1, null));
        assertEquals("Resume or search key cannot be null", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputSearchKeyAndResumeIsEmpty() {
        Path path = Path.of("");
        Resume resume = new Resume("","");
        Exception actual = assertThrows(StorageException.class,
                () -> pathStorage.save(resume, path));
        assertEquals("Search key or resume cannot be empty",actual.getMessage());
    }

    @Test
    void update_shouldReturnException_whenInputResumeDoesNotExist() {
        Resume resume = new Resume("testUuid1", "testFullName");
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> pathStorage.update(resume, pathStorage.getSearchKey(resume.getUuid())));
        assertEquals("Resume " + resume.getUuid() + " does not exist", actual.getMessage());
    }

    @Test
    void update_shouldUpdateResume_whenInputResumeExists() {
        Resume resume4 = new Resume("uuid1","Shara Doe");
        pathStorage.update(resume4, pathStorage.getSearchKey(resume4.getUuid()));

        assertEquals("Shara Doe", pathStorage.get(pathStorage.getSearchKey(resume1.getUuid())).getFullName());
    }

    @Test
    void update_shouldReturnException_whenInputSearchKeyIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> pathStorage.update(resume1, null));
        assertEquals("Resume or search key cannot be null", actual.getMessage());
    }

    @Test
    void update_shouldReturnException_whenInputResumeIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> pathStorage.save(null, null));
        assertEquals("Resume or search key cannot be null", actual.getMessage());
    }

    @Test
    void delete_shouldReturnException_whenInputResumeDoesNotExist() {
        Resume resume = new Resume("testUuid1", "testFullName");
        Exception actual = assertThrows(StorageException.class,
                () -> pathStorage.delete(pathStorage.getSearchKey(resume.getUuid())));
        assertEquals("Path delete exception", actual.getMessage());
    }

    @Test
    void delete_shouldDeleteResume_whenInputResumeExists() {
        pathStorage.delete(pathStorage.getSearchKey(resume1.getUuid()));
        assertFalse(pathStorage.isExist(pathStorage.getSearchKey(resume1.getUuid())));
    }

    @Test
    void delete_shouldReturnException_whenInputSearchKeyIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> pathStorage.delete( null));
        assertEquals("Path cannot be null", actual.getMessage());
    }

    @Test
    void delete_shouldReturnException_whenInputSearchKeyIsEmpty() {
        Exception actual = assertThrows(StorageException.class,
                () -> pathStorage.delete(Path.of("")));
        assertEquals("Path delete exception", actual.getMessage());
    }

    @Test
    void clear_shouldReturnException_whenStorageIsEmpty() {
        deleteDirectory(new File("C:\\Users\\steam\\IdeaProjects\\Java-SE-app\\storage\\testStorage"));
        Exception actual = assertThrows(StorageException.class,
                pathStorage::clear);
        assertEquals("Storage is empty", actual.getMessage());
    }

    @Test
    void clear_shouldClearStorage_whenStorageIsNotEmpty() {
        pathStorage.clear();
        assertEquals(new File(directory).listFiles().length, 0);
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