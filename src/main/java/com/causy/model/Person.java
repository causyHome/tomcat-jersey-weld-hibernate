package com.causy.model;


import com.google.common.collect.ImmutableList;

import java.util.Map;

public class Person {
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final ImmutableList<String> citizenships;
    private final Map<String, Object> creditCards;
    private final int age;

    public Person(String firstName, String lastName, String dateOfBirth, ImmutableList<String> citizenships, Map<String, Object> creditCards, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.citizenships = citizenships;
        this.creditCards = creditCards;
        this.age = age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private ImmutableList<String> citizenships;
        private Map<String, Object> creditCards;
        private int age;

        public Builder withFirstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withDateOfBirht(final String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withCitizenships(final ImmutableList<String> citizenships) {
            this.citizenships = citizenships;
            return this;
        }

        public Builder withCreditCards(final Map<String, Object> creditCards) {
            this.creditCards = creditCards;
            return this;
        }

        public Builder withAge(final int age) {
            this.age = age;
            return this;
        }

        public Person build() {
            return new Person(firstName, lastName, dateOfBirth, citizenships, creditCards, age);
        }
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

    public ImmutableList<String> getCitizenships() {
        return citizenships;
    }

    public Map<String, Object> getCreditCards() {
        return creditCards;
    }

    public int getAge() {
        return age;
    }
}
