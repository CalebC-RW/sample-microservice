package com.robustwealth.microservices.sample.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robustwealth.microservices.sample.biz.Person;
import com.robustwealth.microservices.sample.service.SampleService;

@RestController
@RequestMapping("/api")
public class SampleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    private final SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        super();
        this.sampleService = sampleService;
    }

    @GetMapping(path = "/people")
    public ResponseEntity<List<Person>> getPeople() {
        List<Person> people = this.sampleService.getAllPeople();

        LOGGER.info("Returning {} people", people.size());

        return ResponseEntity.ok(people);
    }

    @GetMapping(path = "/people/{personId}")
    public ResponseEntity<Person> getPerson(@PathVariable("personId") UUID personId) {
        Person person = this.sampleService.getPerson(personId);

        if (person == null) {
            LOGGER.warn("Requested person not returned: {}", personId);
            return ResponseEntity.notFound().build();
        }

        LOGGER.info("Person returned for {}", personId);

        return ResponseEntity.ok(person);
    }
}
