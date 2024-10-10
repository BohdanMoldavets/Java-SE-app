package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.model.Resume;

import java.util.Arrays;

public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex (String uuid) {
        for(int i = 0; i < size; i++) {
            if(uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void subSaveToStorage(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void subDeleteFromStorage(int index) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
    }

    @Override
    protected Resume subElementAtIndex(int index) {
        return storage[index];
    }

    @Override
    protected Resume[] subStorage() {
        return storage;
    }
}
