package com.robustwealth.microservices.sample.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.robustwealth.microservices.sample.biz.Person;

@RequestMapping("/api")
public interface SampleControllerRest {
    @GetMapping(path = "/people")
    public ResponseEntity<List<Person>> getPeople();

    @GetMapping(path = "/people/{personId}")
    public ResponseEntity<Person> getPerson(@PathVariable("personId") UUID personId);

    @PostMapping(path = "/people")
    public ResponseEntity<Void> createPerson(@Valid @RequestBody Person person);
}
