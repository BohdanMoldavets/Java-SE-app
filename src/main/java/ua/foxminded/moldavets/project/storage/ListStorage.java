package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    List<Resume> listStorage = new ArrayList<>();

    @Override
    protected int getIndex(String uuid) {
        if(listStorage.contains(new Resume(uuid))) {
            return listStorage.indexOf(new Resume(uuid));
        }
        return -1;
    }

    @Override
    protected int subGetSize() {
        return listStorage.size();
    }


    @Override
    protected void subSaveToStorage(Resume resume, int index) {
        if(listStorage.size()+1 < storage_limit) {
            listStorage.remove(resume);
            listStorage.add(resume);
        } else {
            throw new StorageException("Cannot save " + resume.getUuid() + " because the list is full", null);
        }
    }

    @Override
    protected void subDeleteFromStorage(int index, String uuid) {
        listStorage.remove(new Resume(uuid));
    }

    @Override
    protected int subGetStorageLimit() {
        return storage_limit;
    }

    @Override
    protected void subClearStorage() {
        if (!listStorage.isEmpty()) {
            listStorage.clear();
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
        return listStorage.get(index);
    }

    @Override
    protected Resume[] subStorage() {
        if(!(listStorage.isEmpty())) {
            return listStorage.toArray(new Resume[0]);
        }
        throw new StorageException("Storage is empty",null);
    }
}
