package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Contact {
    private ContactType type;
    private String value;

    public Contact(ContactType type, String value) {
        this.type = type;
        this.value = value;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return type == contact.type &&
                value.equals(contact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
