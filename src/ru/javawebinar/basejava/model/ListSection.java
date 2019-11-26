package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<String> content = new ArrayList<>();

    public ListSection() {
    }

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
