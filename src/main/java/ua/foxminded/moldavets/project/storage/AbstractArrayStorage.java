package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.ExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.Arrays;

abstract class AbstractArrayStorage extends AbstractStorage {

    protected int size = 0;

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if(index <= -1) {
            if(size + 1 < storage_limit) {
                if(!resume.getUuid().trim().isEmpty()) {
                    subSaveToStorage(resume,size);
                    size++;
                } else {
                    throw new StorageException("Uuid cannot be empty", null);
                }
            } else {
                throw new StorageException("Cannot save "+ resume.getUuid() +" because the list is full", resume.getUuid());
            }
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public Resume[] getAll() {
        if(!(size <= 0)) {
//            for(int i = 0; i < size; i++) {
//                System.out.println("get: " + subGetElement(i, null).toString() + " getAll()");
//            }
            return Arrays.copyOfRange(subStorage(),0, size);
        } else {
            throw new StorageException("Storage is empty", null);
        }
    }

}
