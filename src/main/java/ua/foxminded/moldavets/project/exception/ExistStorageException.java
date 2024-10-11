package ua.foxminded.moldavets.project.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid +" already exist", uuid);
    }
}
