package ru.javawebinar.basejava.model;

public class TextSection extends Section{
    public String title;
    public String content;

    public TextSection(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
