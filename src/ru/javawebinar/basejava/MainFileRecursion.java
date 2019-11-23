package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MainFileRecursion {
    public static void main(String[] args) throws IOException {
        showSubDirs(new File("src"), 0);
    }

    private static void showSubDirs(File file, int i) throws IOException {
        String outline = getOutline(i);
        if (file.isDirectory()) {
            String canonicalPath = file.getCanonicalPath();
            System.out.println(outline + file.getName());
            for (String f : file.list())
                showSubDirs(new File(canonicalPath + File.separator + f), i + 1);
        } else {
            System.out.println(outline + file.getName());
        }
    }

    private static String getOutline(int i) {
        char[] pad = new char[i];
        Arrays.fill(pad, '.');
        return new String(pad);
    }
}

