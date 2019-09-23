package ru.javawebinar.basejava.model;

public class TextSection extends Section {
    private String content;

    public TextSection(String title) {
        setTitle(title);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "title='" + getTitle() + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
