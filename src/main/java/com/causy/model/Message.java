package com.causy.model;

import java.util.Date;

public class Message {
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    private String firstName;
    private String lastName;
    private Date date;
    private String text;

    public Message(String firstName, String lastName, Date date, String text) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.text = text;
    }
}
