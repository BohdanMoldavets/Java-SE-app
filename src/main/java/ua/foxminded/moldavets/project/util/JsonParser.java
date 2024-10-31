package ua.foxminded.moldavets.project.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.foxminded.moldavets.project.model.Section;
import ua.foxminded.moldavets.project.model.SectionType;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JsonParser {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonLocalDateAdapter())
            .registerTypeAdapter(Section.class, new JsonSectionAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {return GSON.fromJson(reader, clazz);}

    public static <T> void write(T object, Writer writer) {GSON.toJson(object, writer);}

}
