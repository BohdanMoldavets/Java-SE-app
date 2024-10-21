package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.model.Resume;

public interface FileStorage<SK> {

    Resume get(SK searchKey);
    SK getSearchKey(String uuid);

    void save(Resume resume, SK searchKey);
    void update(Resume resume, SK searchKey);
    void delete(SK searchKey);
    void clear();

    int getSize();
    boolean isExist(SK searchKey);
}
