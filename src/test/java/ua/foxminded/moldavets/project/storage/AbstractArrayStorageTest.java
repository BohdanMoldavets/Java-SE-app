package ua.foxminded.moldavets.project.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.moldavets.project.exception.ExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

class AbstractArrayStorageTest {
    private Storage storage = new ArrayStorage();
    private static final String UUID_1 = "UUID1";
    private static final String UUID_2 = "UUID2";
    private static final String UUID_3 = "UUID3";
    private static final String UUID_4 = "UUID4";

    @BeforeEach
    void setUp() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void save_shouldReturnException_whenInputContainsNull() {
        Exception actual = assertThrows(NullPointerException.class,
                () -> storage.save(new Resume(null)));
        assertEquals("Uuid cannot be null", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputContainsEmptyString() {
        Exception actual = assertThrows(StorageException.class,
                () -> storage.save(new Resume("")));
        assertEquals("Uuid cannot be empty", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputContainsOneSpaces() {
        Exception actual = assertThrows(StorageException.class,
                () -> storage.save(new Resume(" ")));
        assertEquals("Uuid cannot be empty", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenStorageIsFull() {
        //STORAGE_LIMIT = 10000
        for(int i = 5; i <= 10000; i++) {
            storage.save(new Resume("uuid"+i));
        }
        Exception actual = assertThrows(StorageException.class,
                () -> storage.save(new Resume("UUID4")));
        assertEquals("Cannot save "+ UUID_4 +" because the list is full", actual.getMessage());
    }

    @Test
    void save_shouldReturnException_whenInputContainsExistResume() {
        Exception actual = assertThrows(ExistStorageException.class,
                () -> storage.save(new Resume(UUID_1)));
        assertEquals("Resume " + UUID_1 + " already exist", actual.getMessage());
    }


    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void clear() {
    }

    @Test
    void getAll() {
    }

    @Test
    void get() {
    }
}