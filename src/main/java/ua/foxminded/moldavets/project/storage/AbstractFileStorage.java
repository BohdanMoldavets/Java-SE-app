package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.io.File;
import java.util.Objects;

public class AbstractFileStorage extends AbstractStorage {

    private File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);
        if(!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " isn't a directory");
        }
        if(!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected int getIndex(String uuid) {
        return 0;
    }

    @Override
    protected int subGetSize() {
        return 0;
    }

    @Override
    protected void subSaveToStorage(Resume resume, int index) {

    }

    @Override
    protected void subDeleteFromStorage(int index, String uuid) {

    }

    @Override
    protected int subGetStorageLimit() {
        return 0;
    }

    @Override
    protected void subClearStorage() {

    }

    @Override
    protected void subSetStorageLimit(int storageLimit) {

    }

    @Override
    protected Resume subGetElement(int index, String uuid) {
        return null;
    }

    @Override
    protected Resume[] subStorage() {
        return new Resume[0];
    }
}
