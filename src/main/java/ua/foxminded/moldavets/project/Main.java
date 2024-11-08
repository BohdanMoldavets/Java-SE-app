package ua.foxminded.moldavets.project;

import ua.foxminded.moldavets.project.model.*;
import ua.foxminded.moldavets.project.sql.SqlHelper;
import ua.foxminded.moldavets.project.storage.*;
import ua.foxminded.moldavets.project.storage.serializer.DataStreamSerializer;
import ua.foxminded.moldavets.project.storage.serializer.JsonStreamSerializer;
import ua.foxminded.moldavets.project.storage.serializer.ObjectStreamSerializer;
import ua.foxminded.moldavets.project.util.JsonParser;

import java.io.File;
import java.time.Month;
import java.util.List;

public class Main {

    static Storage ARRAY_STORAGE = new ArrayStorage();
    static Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();
    static Storage LIST_STORAGE = new ListStorage();
    static Storage MAP_STORAGE = new MapStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume("uuid1", "John Doe");
        Resume resume2 = new Resume("uuid2", "Ethan Parker");
        Resume resume3 = new Resume("uuid3", "Olivia Bennett");
        Resume resume4 = new Resume("uuid4", "Jack Sullivan");
        Resume resume5 = new Resume("uuid5", "Sophia Carter");
        Resume resume6 = new Resume("uuid6", "Amply Harris");

        ARRAY_STORAGE.save(resume1);
        ARRAY_STORAGE.save(resume2);
        ARRAY_STORAGE.save(resume3);
        ARRAY_STORAGE.save(resume6);

        SORTED_ARRAY_STORAGE.save(resume4);
        SORTED_ARRAY_STORAGE.save(resume1);
        SORTED_ARRAY_STORAGE.save(resume3);
        SORTED_ARRAY_STORAGE.save(resume2);
        SORTED_ARRAY_STORAGE.save(resume5);
        SORTED_ARRAY_STORAGE.save(resume6);

        LIST_STORAGE.save(resume1);
        LIST_STORAGE.save(resume2);
        LIST_STORAGE.save(resume3);

        MAP_STORAGE.save(resume1);
        MAP_STORAGE.save(resume2);
        MAP_STORAGE.save(resume3);



        resume1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        resume1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        resume1.addSection(SectionType.QUALIFICATIONS, new ListSection("C,Java,SQL"));
        resume1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievement1,Achievement2,Achievement3"));
//        resume1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
//                new Organization("Organization", "https://google.com",
//                        new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
//                        new Organization.Position(2001, Month.MARCH, "position2", "content2")
//                )));
//        resume1.addSection(SectionType.EDUCATION, new OrganizationSection(
//                new Organization("Merito WSB", "https://merito.pl",
//                        new Organization.Position(2007, Month.OCTOBER, "position1", "content1"),
//                        new Organization.Position(2008, Month.MAY, "position2", "content2"))
//        ));


        //resume1.addContact(ContactType.EMAIL,"example@example.com");

        //Config.get().getStorage().save(resume1);
        //System.out.println(Config.get().getStorage().get(resume1.getUuid()).getSections());
        //Config.get().getStorage().delete("uuid1");
        System.out.println(Config.get().getStorage().getSize());

    }
}