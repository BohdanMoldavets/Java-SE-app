package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class PathStorage implements FileStorage<Path> {
    private final Path directory;
    private final StreamSerializer serializer;

    public PathStorage(String directory, StreamSerializer serializer) {
        this.serializer = serializer;
        Objects.requireNonNull(directory, "directory must be not null");
        this.directory = Paths.get(directory);
        if(!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(String.format(directory + " directory must be writable"));
        }
    }

    @Override
    public Resume get(Path searchKey) {
        try {
            return serializer.subRead(new BufferedInputStream(new FileInputStream(String.valueOf(searchKey)))); //sk.toFile()
        } catch (IOException e) {
            throw new StorageException("Path get exception",searchKey.toFile().getName(), e);
        }
    }

    @Override
    public Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    public void save(Resume resume, Path searchKey) {
        try {
            serializer.subWrite(resume, new BufferedOutputStream(new FileOutputStream(getSearchKey(resume.getUuid()).toFile())));
            System.out.println(resume.getUuid() + " saved successfully");
        } catch (IOException e) {
            throw new StorageException("Couldn't save resume ", resume.getUuid(), e);
        }
    }

    @Override
    public void update(Resume resume, Path searchKey) {
        if (isExist(searchKey)) {
            try {
                serializer.subWrite(resume, new BufferedOutputStream(new FileOutputStream(getSearchKey(resume.getUuid()).toFile())));
                System.out.println(resume.getUuid() + " updated successfully");
            } catch (IOException e) {
                throw new StorageException("Couldn't update resume ", resume.getUuid(), e);
            }
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(Path searchKey) {
        try {
            Files.delete(searchKey);
            System.out.printf("File %s deleted successful", searchKey.toFile().getName());
        } catch (IOException e) {
            throw new StorageException("Path delete exception", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::delete);
        } catch (IOException e) {
            throw new StorageException("Path delete exception", null, e);
        }
    }

    @Override
    public int getSize() {
        String[] files = directory.toFile().list();

        if(files == null) {
            throw new StorageException("Storage null exception", null);
        }
        return files.length;
    }

    @Override
    public boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }
}
