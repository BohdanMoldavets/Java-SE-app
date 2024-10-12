package ua.foxminded.moldavets.project;

import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.storage.ArrayStorage;
import ua.foxminded.moldavets.project.storage.SortedArrayStorage;
import ua.foxminded.moldavets.project.storage.Storage;

public class Main {

    static Storage ARRAY_STORAGE = new ArrayStorage();
    static Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume("uuid1");
        Resume resume2 = new Resume("uuid2");
        Resume resume3 = new Resume("uuid3");
        Resume resume4 = new Resume("uuid4");
        Resume resume5 = new Resume("uuid5");
        Resume resume6 = new Resume("");

        ARRAY_STORAGE.save(resume1);
        ARRAY_STORAGE.save(resume2);
        ARRAY_STORAGE.save(resume3);

        SORTED_ARRAY_STORAGE.save(resume4);
        SORTED_ARRAY_STORAGE.save(resume1);
        SORTED_ARRAY_STORAGE.save(resume3);
        SORTED_ARRAY_STORAGE.save(resume2);
        SORTED_ARRAY_STORAGE.save(resume5);
        SORTED_ARRAY_STORAGE.save(resume6);


        System.out.println("--------------Array Storage---------------");
        System.out.println("Get resume1: " + ARRAY_STORAGE.get(resume2.getUuid()));
        System.out.println(ARRAY_STORAGE.getAll());

        System.out.println("--------------Sorted Array Storage---------------");
        System.out.println("Get resume1: " + SORTED_ARRAY_STORAGE.get(resume1.getUuid()));
        SORTED_ARRAY_STORAGE.delete(resume2.getUuid());
        System.out.println(SORTED_ARRAY_STORAGE.getAll());
    }
}