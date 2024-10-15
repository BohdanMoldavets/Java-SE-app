package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    private Resume[] sortedStorage = new Resume[storage_limit];

    @Override
    protected int getIndex(String uuid) {
        if(uuid != null) {
            Resume searchKey = new Resume(uuid);
            return Arrays.binarySearch(sortedStorage,0, size, searchKey);
        } else {
            throw new NullPointerException("Uuid cannot be null");
        }
    }

    @Override
    protected int subGetSize() {
        return size;
    }


    @Override
    protected void subSaveToStorage(Resume resume,int index) {
        sortedStorage[index] = resume;
        sortArray();
    }

    @Override
    protected void subDeleteFromStorage(int index, String uuid) {
            sortedStorage[index] = sortedStorage[size - 1];
            sortedStorage[size - 1] = null;
            size--;
            sortArray();
    }

    @Override
    protected int subGetStorageLimit() {
        return storage_limit;
    }

    @Override
    protected void subClearStorage() {
        if(!(size <= 0)) {
            Arrays.fill(sortedStorage,0, size, null);
            size = 0;
        } else {
            throw new StorageException("Cannot clear storage, because storage is empty", null);
        }
    }

    @Override
    protected void subSetStorageLimit(int storageLimit) {
        storage_limit = storageLimit;
    }


    @Override
    protected Resume subGetElement(int index, String uuid) {
        return sortedStorage[index];
    }

    @Override
    protected Resume[] subStorage() {
        return sortedStorage;
    }


    private void sortArray() {
        sortedStorage = selectionSort(sortedStorage,size);
    }
    // I can use Arrays.sort() here, but the assignment says not to use it lol
    private Resume[] selectionSort(Resume[] array, int size) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size-1; j++) {
                if(array[j].compareTo(array[j+1]) > 0) {
                    Resume temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        return array;
    }
}
