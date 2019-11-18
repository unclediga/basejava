package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private final List<String> content;

    public ListSection(String title, List<String> content) {
        setTitle(title);
        this.content = content;
    }
    public ListSection(String title, String... subsections) {
        this(title, Arrays.asList(subsections));
    }

    public List<String> getContent() {
        return content;
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
