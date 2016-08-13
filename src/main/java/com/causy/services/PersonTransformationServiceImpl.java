package com.causy.services;

import com.causy.model.Person;

public class PersonTransformationServiceImpl implements PersonTransformationService {
    @Override
    public Person anonymize(final Person original) {
        return Person.builder()
                .withFirstName("XXX")
                .withLastName("YYY")
                .withCreditCards(null)
                .withDateOfBirht(original.getDateOfBirth())
                .withCitizenships(original.getCitizenships())
                .withAge(original.getAge())
                .build();
    }
}
