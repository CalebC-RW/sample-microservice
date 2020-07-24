package com.robustwealth.microservices.sample.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.robustwealth.microservices.sample.biz.Person;
import com.robustwealth.microservices.sample.repository.PersonRepository;

@Service
public class SampleService {
    private final PersonRepository personRepository;

    @Autowired
    public SampleService(PersonRepository personRepository) {
        super();
        this.personRepository = personRepository;
    }

    @Nonnull
    public List<Person> getAllPeople() {
        return ImmutableList.copyOf(personRepository.findAll().values());
    }

    @Nullable
    public Person getPerson(UUID personId) {
        return personRepository.findById(personId.toString());
    }
}
