package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private Resume[] sortedStorage = new Resume[storage_limit];

    private static final Comparator<Resume> RESUME_COMPARATOR = ((o1, o2) -> o1.getUuid().compareTo(o2.getUuid()));

    @Override
    protected int getIndex(String uuid) {
        if(uuid != null) {
            Resume searchKey = new Resume(uuid, "null");
            return Arrays.binarySearch(sortedStorage,0, size, searchKey, RESUME_COMPARATOR);
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
        for(int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for(int j = i + 1; j < size; j++) {
                if(RESUME_COMPARATOR.compare(array[j], array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            Resume temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
        return array;
    }
}
