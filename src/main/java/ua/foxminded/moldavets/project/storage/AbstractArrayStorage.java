package ua.foxminded.moldavets.project.storage;

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
                subSaveToStorage(resume,size);
                size++;
            } else {
                System.out.println("Can't save "+ resume +" because the list is full");
            }
        } else {
            System.out.println("Can't save resume " + resume + " because it is already in storage"); //throw exception
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if( index == -1) {
            System.out.println("Can't update " + resume + " because it is not in storage"); //throw exception
        } else {
            subSaveToStorage(resume,index);
            System.out.println(resume + " was updated");
        }
    }

    public void delete (String uuid) {
        int index = getIndex(uuid);
        if( index == -1 ) {
            System.out.println("Can't delete resume " + uuid + " because it is not in storage"); //throw exception
        } else {
            subDeleteFromStorage(index);
        }
    }

    public void clear() {
        Arrays.fill(subStorage(),0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        if(!(size <= 0)) {
            for(int i = 0; i < size; i++) {
                System.out.println("get: " + subElementAtIndex(i).toString() + " getAll()");
            }
            return Arrays.copyOfRange(subStorage(),0, size);
        } else {
            System.out.println("Storage is empty"); // throw new exception
            return null;
        }
    }


    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if( index >= 0 ) {
            return subElementAtIndex(index);
        } else {
            System.out.println("Can't get resume " + uuid + " because it is not in storage"); // throw exception
            return null;
        }

    }


    protected abstract int getIndex(String uuid);
    protected abstract void subSaveToStorage(Resume resume, int index);
    protected abstract void subDeleteFromStorage(int index);
    protected abstract Resume subElementAtIndex(int index);
    protected abstract Resume[] subStorage();
}
