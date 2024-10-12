package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.ExistStorageException;
import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.Arrays;

abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if(index <= -1) {
            if(size + 1 < STORAGE_LIMIT) {
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

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if(!resume.getUuid().trim().isEmpty()) {
            if( index == -1) {
                throw new NotExistStorageException(resume.getUuid());
            } else {
                subSaveToStorage(resume,index);
                System.out.println(resume + " was updated");
            }
        } else {
            throw new StorageException("Uuid cannot be empty", null);
        }
    }

    public void delete (String uuid) {
        int index = getIndex(uuid);
        if(!uuid.trim().isEmpty()) {
            if( index == -1 ) {
                throw new NotExistStorageException(uuid);
            } else {
                subDeleteFromStorage(index);
            }
        } else {
            throw new StorageException("Uuid cannot be empty", null);
        }
    }

    public void clear() {
        if(!(size <= 0)) {
            Arrays.fill(subStorage(),0, size, null);
            size = 0;
        } else {
            throw new StorageException("Cannot clear storage, because storage is empty", null);
        }
    }

    public Resume[] getAll() {
        if(!(size <= 0)) {
            for(int i = 0; i < size; i++) {
                System.out.println("get: " + subElementAtIndex(i).toString() + " getAll()");
            }
            return Arrays.copyOfRange(subStorage(),0, size);
        } else {
            throw new StorageException("Storage is empty", null);
        }
    }


    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if(!uuid.trim().isEmpty()) {
            if( index >= 0 ) {
                return subElementAtIndex(index);
            } else {
                throw new NotExistStorageException(uuid);
            }
        } else {
            throw new StorageException("Uuid cannot be empty", null);
        }
    }

    public int getStorageLimit() {
        return STORAGE_LIMIT;
    }

    public int getSize() {
        return size;
    }

    protected abstract int getIndex(String uuid);
    protected abstract void subSaveToStorage(Resume resume, int index);
    protected abstract void subDeleteFromStorage(int index);
    protected abstract Resume subElementAtIndex(int index);
    protected abstract Resume[] subStorage();
}
