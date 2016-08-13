package com.causy.services;

import com.causy.model.Person;


public interface PersonTransformationService {

    Person anonymize(Person original);
}
