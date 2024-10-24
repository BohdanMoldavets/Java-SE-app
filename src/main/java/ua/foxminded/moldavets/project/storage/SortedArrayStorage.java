package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    private Resume[] sortedStorage = new Resume[STORAGE_LIMIT];

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(sortedStorage,0, size, searchKey);
    }

    @Override
    protected void subSaveToStorage(Resume resume,int index) {
        sortedStorage[index] = resume;
        sortArray();
    }

    @Override
    protected void subDeleteFromStorage(int index) {
            sortedStorage[index] = sortedStorage[size - 1];
            sortedStorage[size - 1] = null;
            size--;
            sortArray();
    }

    @Override
    protected Resume subElementAtIndex(int index) {
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
