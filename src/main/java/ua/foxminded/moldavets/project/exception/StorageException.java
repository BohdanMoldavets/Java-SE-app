package ua.foxminded.moldavets.project.exception;

public class StorageException extends RuntimeException {

    private final String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception exception) {
        super(message, exception);
        this.uuid = uuid;
    }

    public StorageException(Exception e) {
        super(e);
        this.uuid = null;
    }

    public String getUuid() {
        return uuid;
    }

}
