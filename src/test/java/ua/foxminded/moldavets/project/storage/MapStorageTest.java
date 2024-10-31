package ua.foxminded.moldavets.project.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.moldavets.project.exception.ExistStorageException;
import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapStorageTest {
    private final Storage storage = new MapStorage();
    private static final String UUID_1 = "UUID1";
    private static final String UUID_2 = "UUID2";
    private static final String UUID_3 = "UUID3";
    private static final String UUID_4 = "UUID4";

    @BeforeEach
    void setUp() {
        storage.save(new Resume(UUID_1,"John Doe"));
        storage.save(new Resume(UUID_2, "Jack Jones"));
        storage.save(new Resume(UUID_3, "Kayla Noris"));
    }

    @Test
    void save_shouldReturnException_whenInputContainsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> storage.save(new Resume(null, "none")));
        assertEquals("Uuid cannot be null", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputContainsEmptyString() {
        Exception actual = assertThrows(StorageException.class,
                () -> storage.save(new Resume("", "")));
        assertEquals("Uuid cannot be empty", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputContainsOneSpaces() {
        Exception actual = assertThrows(StorageException.class,
                () -> storage.save(new Resume(" ", " ")));
        assertEquals("Uuid cannot be empty", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenStorageIsFull() {
        //storage.getStorageLimit();
        // STORAGE_LIMIT = 10000, but I'll change it, for test
        storage.setStorageLimit(100);
        for(int i = storage.getSize(); i <= storage.getStorageLimit()-2; i++) {
            storage.save(new Resume("uuid"+i, ""+i));
        }
        Exception actual = assertThrows(StorageException.class,
                () -> storage.save(new Resume("UUID4","none")));
        assertEquals("Cannot save "+ UUID_4 +" because the list is full", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputContainsExistResume() {
        Exception actual = assertThrows(ExistStorageException.class,
                () -> storage.save(new Resume(UUID_1,"none")));
        assertEquals("Resume " + UUID_1 + " already exist", actual.getMessage());
    }

    @Test
    void save_shouldSaveResumeButIfYouTryDoItAgainShouldThrowException_whenInputContainsCorrectResume() {
        storage.save(new Resume(UUID_4,"none"));
        Exception actual = assertThrows(ExistStorageException.class,
                () -> storage.save(new Resume(UUID_4,"none")));
        assertEquals("Resume " + UUID_4 + " already exist", actual.getMessage());
    }

    @Test
    void update_shouldReturnException_whenInputResumeDoesNotExist() {
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> storage.update(new Resume(UUID_4,"none")));
        assertEquals("Resume " + UUID_4 + " does not exist", actual.getMessage());
    }

    @Test
    void update_shouldReturnException_whenInputContainsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> storage.update(new Resume(null,"none")));
        assertEquals("Uuid cannot be null", actual.getMessage());
    }

    @Test
    void update_shouldReturnException_whenInputUuidOfResumeContainsEmptyString() {
        Resume resume = new Resume("","none");
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> storage.update(resume));
        assertEquals("Resume " + resume.getUuid() + " does not exist", actual.getMessage());
    }

    @Test
    void update_shouldReturnException_whenInputUuidOfResumeContainsOneSpaces() {
        Resume resume = new Resume(" ","none");
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> storage.update(new Resume(resume.getUuid(),"none")));
        assertEquals("Resume " + resume.getUuid() + " does not exist", actual.getMessage());
    }

    @Test
    void delete_shouldReturnException_whenInputResumeDoesNotExist() {
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> storage.delete(UUID_4));
        assertEquals("Resume " + UUID_4 + " does not exist", actual.getMessage());
    }

    @Test
    void delete_shouldReturnException_whenInputContainsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> storage.delete(null));
        assertEquals("Uuid cannot be null", actual.getMessage());
    }

    @Test
    void delete_shouldReturnException_whenInputUuidOfResumeIsEmpty() {
        Resume resume = new Resume("","none");
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> storage.delete(resume.getUuid()));
        assertEquals("Resume " +resume.getUuid() + " does not exist", actual.getMessage());
    }

    @Test
    void delete_shouldReturnException_whenInputUuidOfResumeContainsOneSpaces() {
        Resume resume = new Resume(" ","none");
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> storage.delete(resume.getUuid()));
        assertEquals("Resume " + resume.getUuid() + " does not exist", actual.getMessage());
    }

    @Test
    void delete_shouldDeleteResumeAndThrowExceptionWhenYouTryDoItAgain_whenInputResumeExistInStorage() {
        storage.delete(UUID_1);
        Exception actual = assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_1));
        assertEquals("Resume " + UUID_1 + " does not exist", actual.getMessage());
    }

    @Test
    void clear_shouldClearStorage_whenYouInvokeMethod() {
        storage.clear();
        //if the storage is cleared, the method will throw an exception when used again.
        Exception actual = assertThrows(StorageException.class, storage::clear);
        assertEquals("Cannot clear storage, because storage is empty", actual.getMessage());
    }

    @Test
    void getAll_shouldReturnException_whenStorageIsEmpty() {
        storage.clear();
        Exception actual = assertThrows(StorageException.class, storage::getAllSorted);
        assertEquals("Storage is empty", actual.getMessage());
    }

    @Test
    void getAll_shouldReturnTrue_whenStorageEqualsExpectedResult() {
        boolean result = false;
        List<Resume> expectedResumes = new ArrayList<>();
        expectedResumes.add(new Resume(UUID_1,"John Doe"));
        expectedResumes.add(new Resume(UUID_2,"Jack Jones"));
        expectedResumes.add(new Resume(UUID_3,"Kayla Noris"));
        List<Resume> actualResumes = storage.getAllSorted();

        for(Resume r : actualResumes) {
            if(expectedResumes.contains(r)) {
                result = true;
                break;
            }
        }
        assertTrue(result);
    }

    @Test
    void get_shouldReturnException_whenInputResumeDoesNotExist() {
        Exception actual = assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
        assertEquals("Resume " + UUID_4 + " does not exist", actual.getMessage());
    }

    @Test
    void get_shouldReturnException_whenInputResumeIsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> storage.get(null));
        assertEquals("Uuid cannot be null", actual.getMessage());
    }

    @Test
    void get_shouldReturnException_whenInputUuidOfResumeIsEmpty() {
        Resume resume = new Resume("","none");
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> storage.get(resume.getUuid()));
        assertEquals("Resume " + resume.getUuid() + " does not exist", actual.getMessage());
    }

    @Test
    void get_shouldReturnException_whenInputUuidOfResumeContainsOneSpaces() {
        Resume resume = new Resume(" ","none");
        Exception actual = assertThrows(NotExistStorageException.class,
                () -> storage.get(resume.getUuid()));
        assertEquals("Resume " + resume.getUuid() + " does not exist", actual.getMessage());
    }

    @Test
    void get_shouldReturnResume_whenInputResumeExistInStorage() {
        assertEquals(new Resume(UUID_1,"John Doe"), storage.get(UUID_1));
    }
}