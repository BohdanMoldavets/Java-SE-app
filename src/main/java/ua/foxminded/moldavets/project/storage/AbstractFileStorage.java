package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.StorageException;
import ua.foxminded.moldavets.project.model.Resume;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractFileStorage implements FileStorage<File> {

    private final File directory;

    public AbstractFileStorage(File directory) {
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
            return isExist(file) ? subRead(new BufferedInputStream(new FileInputStream(file))) : null;
        } catch (IOException e) {
            throw new StorageException("File read exception", file.getName(), e);
        }
    }

    @Override
    public void save(Resume resume, File file) {
        try {
            file.createNewFile();
            //subWrite(resume,file);
        } catch (IOException exception) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath() , file.getName(), exception);
        }
        subUpdate(resume, file);
    }

    @Override
    public void update(Resume resume, File file) {
        try {
            subWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO exception", file.getName(), e);
        }
    }

    @Override
    public void delete(File file) {
        if(!file.delete()) {
            throw new StorageException("File delete exception", file.getName());
        }
    }

    @Override
    public void clear() {
        File[] directoryArrayFiles = directory.listFiles();
        if (directoryArrayFiles != null) {
            Arrays.stream(directoryArrayFiles).forEach(this::subDelete);
        }

//        for(File file : directoryArrayFiles) {
//
//            subDelete(file);
//        }

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

    protected abstract void subWrite(Resume resume, OutputStream file) throws IOException;
    protected abstract Resume subRead(InputStream file) throws IOException;
    protected abstract void subDelete(File file);
    protected abstract void subUpdate(Resume resume, File file);

}
