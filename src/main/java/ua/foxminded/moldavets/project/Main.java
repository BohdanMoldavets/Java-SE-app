package ua.foxminded.moldavets.project;

import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.storage.ArrayStorage;

public class Main {

    static ArrayStorage arrayStorage = new ArrayStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume();
        Resume resume2 = new Resume();
        Resume resume3 = new Resume();


        resume1.setUuid("uuid1");
        resume2.setUuid("uuid2");
        resume3.setUuid("uuid3");

        arrayStorage.save(resume1);
        arrayStorage.save(resume2);
        arrayStorage.save(resume3);



        System.out.println("Get resume1: " + arrayStorage.get(resume1.getUuid()));

        System.out.println(arrayStorage.getAll());

        arrayStorage.delete("uuid3");

        System.out.println(arrayStorage.getAll());
    }
}