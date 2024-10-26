package ua.foxminded.moldavets.project.storage.serializer;

import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.util.JsonParser;

import java.io.*;

public class JsonStreamSerializer implements StreamSerializer {
    @Override
    public void subWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume subRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream)) {
            return  JsonParser.read(reader, Resume.class);
        }
    }
}
