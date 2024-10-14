package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<Resume,String> mapStorage = new HashMap<Resume,String>();

    @Override
    protected int getIndex(String uuid) {
        if(mapStorage.containsKey(new Resume(uuid))) {
            return 1;
        }
        return -1;
    }

    @Override
    protected int subGetSize() {
        return mapStorage.size();
    }


    @Override
    protected void subSaveToStorage(Resume resume, int index) {
        mapStorage.remove(resume);
        mapStorage.put(resume, resume.getUuid());
    }

    @Override
    protected void subDeleteFromStorage(int index, String uuid) {
        mapStorage.remove(new Resume(uuid));
    }

    @Override
    protected void subClearStorage() {
        mapStorage.clear();
    }

    @Override
    protected Resume subGetElement(int index, String uuid) {
        return new Resume(mapStorage.get(new Resume(uuid)));
    }

    @Override
    protected Resume[] subStorage() {
        if(!(mapStorage.isEmpty())) {
            return mapStorage.keySet().toArray(new Resume[0]);
        }
        throw new StorageException("Storage is empty", null);
    }
}
