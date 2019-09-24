package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(content, that.content) && Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, getTitle());
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "title='" + getTitle() + '\'' +
                ", content=" + content +
                '}';
    }
}
