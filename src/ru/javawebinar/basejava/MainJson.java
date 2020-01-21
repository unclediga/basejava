package ru.javawebinar.basejava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class MainJson {
    private static Gson GSON = new GsonBuilder()
//            .registerTypeAdapter(AbstractSection.class,new JsonSectionAdapter())
            .create();

    public static void main(String[] args) {
        Object a = GSON.fromJson("{\"a\":\"123\"}", HashMap.class);
        System.out.println(a + a.getClass().getCanonicalName());
    }
}
