package ua.foxminded.moldavets.project.util;

import java.io.File;
import java.util.Objects;

public class FileTree {

    public FileTree() {

    }

    public FileTree(File directory, int offset) {
        treeList(directory, offset);
    }

    public void treeList(File dir, int offset) {
        File[] files = dir.listFiles();

        for (File p : Objects.requireNonNull(files)) {
            if (p != null && !p.getName().equals("objects")) {
                if(p.isFile()){
                    System.out.println(repeatCharSpace(offset) + "F: " + p.getName());
                } else if (p.isDirectory()) {
                    System.out.println(repeatCharSpace(offset) + "D: " + p.getName());
                    treeList(p, offset + 2);
                }
            }
        }

    }

    private static String repeatCharSpace(int count) {
        return String.valueOf(' ')
                .repeat(Math.max(0, count));
    }

}
