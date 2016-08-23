package com.causy.model;

public class Message {
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    private final String firstName;
    private final String lastName;
    private final long date;
    private final String text;

    public Message(final String firstName, final String lastName, final long date, String text) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.text = text;
    }
}
