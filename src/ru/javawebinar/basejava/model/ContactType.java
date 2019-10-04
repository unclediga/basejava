package ru.javawebinar.basejava.model;

public enum ContactType {
    ADDRESS("Адрес"),
    TELEPHONE("Тел."),
    EMAIL("e-mail"),
    SKYPE("Skype"),
    GITHUB("GitHub"),
    LINKEDIN("LinkedIn"),
    STACKOVERFLOW("StackOverflow"),
    WEBLINK("Home Page");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
