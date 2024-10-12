package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.model.Resume;

public interface Storage {

    Resume get(String uuid);
    Resume[] getAll();

    void save(Resume resume);
    void update(Resume resume);
    void delete(String uuid);
    void clear();

    int getStorageLimit();
    int getSize();
}
