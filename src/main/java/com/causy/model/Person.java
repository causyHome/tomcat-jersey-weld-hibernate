package com.causy.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Person {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String[] citizenships;

    public Person(String firstName, String lastName, String dateOfBirth, String[] citizenships, Map<String, Object> creditCards, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.citizenships = citizenships;
        this.creditCards = creditCards;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String[] getCitizenships() {
        return citizenships;
    }

    public Map<String, Object> getCreditCards() {
        return creditCards;
    }

    public int getAge() {
        return age;
    }
    private Map<String, Object> creditCards;

    private int age;
}
