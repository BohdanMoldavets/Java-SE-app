package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {


    public ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void subWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Resume subRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
