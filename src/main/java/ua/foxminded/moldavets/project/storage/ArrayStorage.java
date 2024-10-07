package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.model.Resume;

public class ArrayStorage {

    private static int STORAGE_SIZE = 10000;

    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int size = 0;

    public void save(Resume resume) {
        if(!(isResumeInStorage(resume))) {
            if(size+1 < STORAGE_SIZE) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Can't save "+ resume +" because the list is full");
            }
        } else {
            System.out.println("Can't save resume " + resume + " because it is already in storage"); //throw exception
        }
    }

    public void update(Resume resume) {
        if(isResumeInStorage(resume)){
            for(int i = 0; i < size; i++) {
                if(storage[i].equals(resume)) {
                    storage[i] = resume;
                    System.out.println(resume + " was updated");
                }
            }
        } else {
            System.out.println("Can't update " + resume + " because it is not in storage"); //throw exception
        }
    }

    public void delete (String uuid) {
        if(isResumeInStorageByUuid(uuid)) {
            for(int i = 0; i < size; i++) {
                if(storage[i].getUuid().equals(uuid)) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                }
            }
        } else {
            System.out.println("Can't delete resume " + uuid + " because it is not in storage"); //throw exception
        }
    }

    public void clear() {
        for(int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public Resume get(String uuid) {
        if(isResumeInStorageByUuid(uuid)) {
            for(int i = 0; i < size; i++) {
                if(storage[i].getUuid().equals(uuid)) {
                    return storage[i];
                }
            }
        } else {
            System.out.println("Can't get resume " + uuid + " because it is not in storage"); // throw exception
        }
        return null;
    }


    public int getSize(){
        return size;
    }

    public Resume[] getAll() {
        if(!(size <= 0)) {
            Resume[] resumes = new Resume[size];
            for(int i = 0; i < size; i++) {
                resumes[i] = storage[i];
                System.out.println("get: " + resumes[i].toString());
            }
            return resumes;
        } else {
            System.out.println("Storage is empty"); // throw new exception
            return null;
        }
    }

    private boolean isResumeInStorage(Resume resume) {
        for(int i = 0; i < size; i++) {
            if(storage[i].equals(resume)) {
                return true;
            }
        }
        return false;
    }

    private boolean isResumeInStorageByUuid(String uuid) {
        for(int i = 0; i < size; i++) {
            if(storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

}
