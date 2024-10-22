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
    public void save(Resume resume, File dir) {
        try {
            dir.createNewFile();
            subWrite(resume, new BufferedOutputStream(new FileOutputStream(String.valueOf(directory+"\\"+resume.getFullName()))));
            System.out.printf("File %s saved successful%n",resume.getFullName());
        } catch (IOException exception) {
            throw new StorageException("Couldn't create file " + dir.getAbsolutePath()+"\\"+resume.getFullName(),
                    dir.getName(), exception);
        }
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
        System.out.printf("File %s deleted successful%n",file.getName());
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

    protected abstract void subWrite(Resume resume, OutputStream outputStream) throws IOException;
    protected abstract Resume subRead(InputStream inputStream) throws IOException;

}
