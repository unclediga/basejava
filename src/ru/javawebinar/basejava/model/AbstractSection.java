package ru.javawebinar.basejava.model;

import java.io.Serializable;

public abstract class AbstractSection implements Serializable {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
