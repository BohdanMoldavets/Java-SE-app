package ua.foxminded.moldavets.project;

import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.storage.ArrayStorage;
import ua.foxminded.moldavets.project.storage.SortedArrayStorage;
import ua.foxminded.moldavets.project.storage.Storage;

public class Main {

    static Storage ARRAY_STORAGE = new ArrayStorage();
    static Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume();
        Resume resume2 = new Resume();
        Resume resume3 = new Resume();
        Resume resume4 = new Resume();
        Resume resume5 = new Resume();


        resume1.setUuid("uuid1");
        resume2.setUuid("uuid2");
        resume3.setUuid("uuid3");
        resume4.setUuid("uuid4");
        resume5.setUuid("uuid5");

        ARRAY_STORAGE.save(resume1);
        ARRAY_STORAGE.save(resume2);
        ARRAY_STORAGE.save(resume3);

        SORTED_ARRAY_STORAGE.save(resume4);
        SORTED_ARRAY_STORAGE.save(resume1);
        SORTED_ARRAY_STORAGE.save(resume3);
        SORTED_ARRAY_STORAGE.save(resume2);
        SORTED_ARRAY_STORAGE.save(resume5);


        System.out.println("--------------1---------------");
        System.out.println("Get resume1: " + ARRAY_STORAGE.get(resume2.getUuid()));
        System.out.println(ARRAY_STORAGE.getAll());

        System.out.println("--------------2---------------");
        System.out.println("Get resume1: " + SORTED_ARRAY_STORAGE.get(resume1.getUuid()));
        SORTED_ARRAY_STORAGE.delete(resume2.getUuid());
        System.out.println(SORTED_ARRAY_STORAGE.getAll());
    }
}