package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<Resume,String> mapStorage = new HashMap<Resume,String>();

    @Override
    protected int getIndex(String uuid) {
        for(Resume r : mapStorage.keySet()){
            if(uuid.equals(r.getUuid())){
                return 1;
            }
        }
        return -1;
    }

    @Override
    protected int subGetSize() {
        return mapStorage.size();
    }


    @Override
    protected void subSaveToStorage(Resume resume, int index) {
        if(mapStorage.size()+1 < storage_limit) {
            mapStorage.remove(resume);
            mapStorage.put(resume, resume.getUuid());
        } else {
            throw new StorageException("Cannot save " + resume.getUuid() + " because the list is full", null);
        }
    }

    @Override
    protected void subDeleteFromStorage(int index, String uuid) {
        for(Resume r : mapStorage.keySet()){
            if(uuid.equals(r.getUuid())){
                mapStorage.remove(r);
            }
        }
    }

    @Override
    protected int subGetStorageLimit() {
        return storage_limit;
    }


    @Override
    protected void subClearStorage() {
        if(!(mapStorage.isEmpty())) {
            mapStorage.clear();
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
        for(Resume r : mapStorage.keySet()) {
            if(uuid.equals(r.getUuid())) {
                return r;
            }
        }
        return null;
    }

    @Override
    protected Resume[] subStorage() {
        if(!(mapStorage.isEmpty())) {
            return mapStorage.keySet().toArray(new Resume[0]);
        }
        throw new StorageException("Storage is empty", null);
    }
}
