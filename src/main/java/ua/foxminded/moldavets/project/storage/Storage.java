package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.model.Resume;

import java.util.List;

public interface Storage {

    Resume get(String uuid);
    List<Resume> getAllSorted();

    void save(Resume resume);
    void update(Resume resume);
    void delete(String uuid);
    void clear();
    void setStorageLimit(int storageLimit);

    int getStorageLimit();
    int getSize();
}
