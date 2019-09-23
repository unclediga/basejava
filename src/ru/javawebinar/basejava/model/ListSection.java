package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {
    private List<String> content = new ArrayList<>();

    public ListSection(String title) {
        setTitle(title);
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
    public void addSubsection(String subsection){
        content.add(subsection);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "title='" + getTitle() + '\'' +
                ", content=" + content +
                '}';
    }
}
