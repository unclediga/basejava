package ru.javawebinar.basejava.model;

public class Organization {
    private String title;
    private Contact homeLink;

    public Organization(String title, String homeLink) {
        this.title = title;
        this.homeLink = new Contact(ContactType.WEBLINK, "Official web-page", homeLink);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", homeLink=" + homeLink +
                '}';
    }
}
