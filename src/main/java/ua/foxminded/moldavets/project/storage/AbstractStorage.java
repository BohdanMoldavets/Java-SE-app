package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.ExistStorageException;
import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.Arrays;

abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if(index == -1) {
            if(!resume.getUuid().trim().isEmpty() || resume.getUuid() != null) {
                subSaveToStorage(resume,index);
            } else {
                throw new StorageException("Uuid cannot be null or empty",null);
            }
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if(index != -1) {
            return subGetElement(index, uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return subStorage();
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if(index != -1) {
            subSaveToStorage(resume,index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if(index != -1) {
            subDeleteFromStorage(index, uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void clear() {
        subClearStorage();
    }

    @Override
    public int getStorageLimit() {
        return -1;
    }

    @Override
    public int getSize() {
        return subGetSize();
    }

    protected abstract int getIndex(String uuid);
    protected abstract int subGetSize();
    protected abstract void subSaveToStorage(Resume resume, int index);
    protected abstract void subDeleteFromStorage(int index, String uuid);
    protected abstract void subClearStorage();
    protected abstract Resume subGetElement(int index, String uuid);
    protected abstract Resume[] subStorage();
}
