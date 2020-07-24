package com.robustwealth.microservices.sample.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.robustwealth.microservices.sample.biz.Person;
import com.robustwealth.microservices.sample.repository.PersonRepository;

@Service
public class SampleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleService.class);

    private final PersonRepository personRepository;

    @Autowired
    public SampleService(PersonRepository personRepository) {
        super();
        this.personRepository = personRepository;
    }

    @Nonnull
    public List<Person> getAllPeople() {
        return ImmutableList.of();
    }

    @Nullable
    public Person getPerson(UUID personId) {
        return null;
    }
}
