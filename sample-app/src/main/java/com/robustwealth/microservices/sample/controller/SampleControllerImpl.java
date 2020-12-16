package com.robustwealth.microservices.sample.controller;

import com.robustwealth.microservices.sample.biz.Person;
import com.robustwealth.microservices.sample.service.SampleService;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleControllerImpl implements SampleControllerRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleControllerImpl.class);

    private final SampleService sampleService;

    @Autowired
    public SampleControllerImpl(SampleService sampleService) {
        super();
        this.sampleService = sampleService;
    }

    @Override
    public ResponseEntity<List<Person>> getPeople() {
        final List<Person> people = this.sampleService.getAllPeople();

        LOGGER.info("Returning {} people now", people.size());

        return ResponseEntity.ok(people);
    }

    @Override
    public ResponseEntity<Person> getPerson(UUID personId) {
        final Person person = this.sampleService.getPerson(personId);

        if (person == null) {
            LOGGER.warn("Requested person not returned by service: {}. This may be correct behavior.", personId);
            return ResponseEntity.notFound().build();
        }

        LOGGER.info("Person returned for {}", personId);

        return ResponseEntity.ok(person);
    }

    @Override
    public ResponseEntity<Void> createPerson(Person person) {
        return ResponseEntity.ok().build();
    }
}
