package ru.javawebinar.basejava.model;

import java.util.Objects;

public class OrganizationInfo {
    private String title;
    private String link;

    public OrganizationInfo(String title, String link) {
        Objects.requireNonNull(title, "title must not be null");
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationInfo that = (OrganizationInfo) o;
        return title.equals(that.title) &&
                Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link);
    }

    @Override
    public String toString() {
        return "OrganizationLink{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
