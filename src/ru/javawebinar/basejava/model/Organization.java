package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Organization {
    private String title;
    private Contact homeLink;

    public Organization(String title, String homeLink) {
        this.title = title;
        this.homeLink = new Contact(ContactType.WEBLINK, homeLink);
    }

    public String getTitle() {
        return title;
    }

    public Contact getHomeLink() {
        return homeLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(homeLink, that.homeLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, homeLink);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", homeLink=" + homeLink +
                '}';
    }
}
