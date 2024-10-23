package ua.foxminded.moldavets.project.storage.serializer;

import ua.foxminded.moldavets.project.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {

    void subWrite(Resume resume, OutputStream outputStream) throws IOException;
    Resume subRead(InputStream inputStream) throws IOException;

}
