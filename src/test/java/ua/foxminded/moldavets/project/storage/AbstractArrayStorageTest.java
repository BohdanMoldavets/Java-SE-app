package ua.foxminded.moldavets.project.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.moldavets.project.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "UUID1";
    private static final String UUID_2 = "UUID2";
    private static final String UUID_3 = "UUID3";

    @BeforeEach
    void setUp() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void save_shouldReturnException_whenInputContainsNull() {
        storage.save(new Resume(null));
        assertThrows(NullPointerException.class,
                () -> storage.save(new Resume(null)));
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