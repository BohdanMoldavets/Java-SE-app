package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.ExistStorageException;
import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.Arrays;

abstract class AbstractStorage implements Storage {

    protected static int storage_limit = 10000;

    @Override
    public void save(Resume resume) {
        if(resume.getUuid() != null) {
            int index = getIndex(resume.getUuid());
            if(index <= -1) {
                if(!(resume.getUuid().trim().isEmpty())) {
                    subSaveToStorage(resume,index);
                } else {
                    throw new StorageException("Uuid cannot be empty",null);
                }
            } else {
                throw new ExistStorageException(resume.getUuid());
            }
        } else {
            throw new NullPointerException("Uuid cannot be null");
        }
    }

    @Override
    public Resume get(String uuid) {
        if(uuid != null) {
            int index = getIndex(uuid);
            if(index > -1) {
                return subGetElement(index, uuid);
            } else {
                throw new NotExistStorageException(uuid);
            }
        } else {
            throw new NullPointerException("Uuid cannot be null");
        }
    }

    @Override
    public Resume[] getAll() {
        return subStorage();
    }

    @Override
    public void update(Resume resume) {
        if(resume.getUuid() != null) {
            int index = getIndex(resume.getUuid());
            if(index > -1) {
                subSaveToStorage(resume,index);
            } else {
                throw new NotExistStorageException(resume.getUuid());
            }
        } else {
            throw new NullPointerException("Uuid cannot be null");
        }
    }

    @Override
    public void delete(String uuid) {
        if(uuid != null) {
            int index = getIndex(uuid);
            if(index > -1) {
                subDeleteFromStorage(index, uuid);
            } else {
                throw new NotExistStorageException(uuid);
            }
        } else {
            throw new NullPointerException("Uuid cannot be null");
        }
    }

    @Override
    public void clear() {
        subClearStorage();
    }

    @Override
    public void setStorageLimit(int storageLimit) {
        subSetStorageLimit(storageLimit);
    }

    @Override
    public int getStorageLimit() {
        return subGetStorageLimit();
    }

    @Override
    public int getSize() {
        return subGetSize();
    }

    protected abstract int getIndex(String uuid);
    protected abstract int subGetSize();
    protected abstract void subSaveToStorage(Resume resume, int index);
    protected abstract void subDeleteFromStorage(int index, String uuid);

    protected abstract int subGetStorageLimit();
    protected abstract void subClearStorage();
    protected abstract void subSetStorageLimit(int storageLimit);
    protected abstract Resume subGetElement(int index, String uuid);
    protected abstract Resume[] subStorage();

}
