package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class FileStorageImpl implements FileStorage<File> {

    private final File directory;
    private final StreamSerializer serializer;

    public FileStorageImpl(File directory, StreamSerializer serializer) {
        this.serializer = serializer;
        Objects.requireNonNull(directory);
        if(!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " isn't a directory");
        }
        if(!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public Resume get(File file) {
        try {
            return isExist(file) ? serializer.subRead(new BufferedInputStream(new FileInputStream(file))) : null;
        } catch (IOException e) {
            throw new StorageException("File read exception", file.getName(), e);
        }
    }

    @Override
    public void save(Resume resume, File dir) {
        try {
            //dir.createNewFile();
            serializer.subWrite(resume, new BufferedOutputStream(new FileOutputStream(directory + "\\" + resume.getUuid())));
            System.out.printf("File %s saved successful%n",resume.getUuid());
        } catch (IOException exception) {
            throw new StorageException("Couldn't save resume " + dir.getAbsolutePath()+"\\"+resume.getUuid(),
                    dir.getName(), exception);
        }
    }

    @Override
    public void update(Resume resume, File file) {
        if (isExist(file)) {
            try {
                serializer.subWrite(resume, new BufferedOutputStream(new FileOutputStream(directory + "\\" + resume.getUuid())));
            } catch (IOException e) {
                throw new StorageException("IO exception", file.getName(), e);
            }
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(File file) {
        if(!file.delete()) {
            throw new StorageException("File delete exception", file.getName());
        }
        System.out.printf("File %s deleted successful",file.getName());
    }

    @Override
    public void clear() {
        if (directory.listFiles() != null) {
            Arrays.asList(Objects.requireNonNull(directory.listFiles())).forEach(this::delete);
        }
    }

    @Override
    public boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public int getSize() {
        String[] files = directory.list();
        if(files == null) {
            throw new StorageException("Storage null exception", null);
        }
        return files.length;
    }
}