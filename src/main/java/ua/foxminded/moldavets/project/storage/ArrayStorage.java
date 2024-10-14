package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.Arrays;

public class ArrayStorage extends AbstractArrayStorage {

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int getIndex (String uuid) {
        if(uuid != null) {
            for(int i = 0; i < size; i++) {
                if(uuid.equals(storage[i].getUuid())) {
                    return i;
                }
            }
            return -1;
        } else {
            throw new NullPointerException("Uuid cannot be null");
        }
    }

    @Override
    protected int subGetSize() {
        return size;
    }


    @Override
    protected void subSaveToStorage(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void subDeleteFromStorage(int index, String uuid) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
    }

    @Override
    protected void subClearStorage() {
        if(!(size <= 0)) {
            Arrays.fill(storage,0, size, null);
            size = 0;
        } else {
            throw new StorageException("Cannot clear storage, because storage is empty", null);
        }
    }

    @Override
    protected Resume subGetElement(int index, String uuid) {
        return storage[index];
    }

    @Override
    protected Resume[] subStorage() {
        return storage;
    }
}
