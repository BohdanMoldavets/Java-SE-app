package ua.foxminded.moldavets.project;

import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.model.SectionType;
import ua.foxminded.moldavets.project.storage.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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


//        System.out.println("--------------Array Storage---------------");
//        System.out.println("Get resume1: " + ARRAY_STORAGE.get(resume2.getUuid()));
//        System.out.println(ARRAY_STORAGE.getAll());
//
//        System.out.println("--------------Sorted Array Storage---------------");
//        System.out.println("Get resume1: " + SORTED_ARRAY_STORAGE.get(resume1.getUuid()));
//        SORTED_ARRAY_STORAGE.delete(resume2.getUuid());
        //System.out.println(SORTED_ARRAY_STORAGE.getAll());

        for(Resume r : LIST_STORAGE.getAllSorted()) {
            System.out.println(r);
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }


//        System.out.println("--------------List Storage---------------");
//
//        Resume[] list = LIST_STORAGE.getAll();
//        LIST_STORAGE.delete("uuid1");
//        for (Resume resume : list) {
//            System.out.println(resume);
//        }
//        System.out.println(LIST_STORAGE.get("uuid1"));

//        System.out.println("--------------Map Storage---------------");
//        Resume[] map = MAP_STORAGE.getAll();
//
//
//        MAP_STORAGE.update(resume1);
//        for (Resume resume : map) {
//            System.out.println(resume);
//        }
    }
}