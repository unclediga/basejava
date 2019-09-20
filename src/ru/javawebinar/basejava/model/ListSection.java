package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {
    public java.lang.String title;
    private List<String> content = new ArrayList<>();

    public ListSection(java.lang.String title) {
        this.title = title;
    }
    public void addSubsection(String subsection){
        content.add(subsection);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "title='" + title + '\'' +
                ", content=" + content +
                '}';
    }
}
