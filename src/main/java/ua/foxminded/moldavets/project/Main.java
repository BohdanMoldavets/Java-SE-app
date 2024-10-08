package ua.foxminded.moldavets.project;

import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.storage.ArrayStorage;

public class Main {

    static ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume();
        Resume resume2 = new Resume();
        Resume resume3 = new Resume();


        resume1.setUuid("uuid1");
        resume2.setUuid("uuid2");
        resume3.setUuid("uuid3");

        ARRAY_STORAGE.save(resume1);
        ARRAY_STORAGE.save(resume2);
        ARRAY_STORAGE.save(resume3);



        System.out.println("Get resume1: " + ARRAY_STORAGE.get(resume1.getUuid()));

        ARRAY_STORAGE.getAll();

        ARRAY_STORAGE.delete("uuid3");

        ARRAY_STORAGE.getAll();

        resume2.setUuid("uuid4");
        ARRAY_STORAGE.update(resume2);

        ARRAY_STORAGE.getAll();

    }
}