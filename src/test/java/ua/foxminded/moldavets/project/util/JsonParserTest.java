package ua.foxminded.moldavets.project.util;

import org.junit.jupiter.api.Test;
import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.model.Section;
import ua.foxminded.moldavets.project.model.TextSection;


import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void write_shouldReturnValidJsonSection_whenInputContainsValidSection() {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        Section section2 = JsonParser.read(json, Section.class);
        assertEquals(section1, section2);
    }

    @Test
    void write_shouldReturnValidJsonResume_whenInputContainsValidResume() {
        String json = JsonParser.write(new Resume("123", "Test"), Resume.class);
        assertEquals(new Resume("123", "Test"), JsonParser.read(json, Resume.class));
    }

}