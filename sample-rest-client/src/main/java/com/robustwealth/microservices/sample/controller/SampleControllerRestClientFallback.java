package com.robustwealth.microservices.sample.controller;

import com.google.common.collect.ImmutableList;
import com.robustwealth.microservices.sample.biz.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/does-not-exist") // Necessary to prevent ambiguous mapping exception
public class SampleControllerRestClientFallback implements SampleControllerRestClient {

    @Override
    public ResponseEntity<List<Person>> getPeople() {
        return ResponseEntity
                .of(Optional.of(ImmutableList.of(Person.builder().firstName("Unknown").lastName("Unknown").build())));
    }

    @Override
    public ResponseEntity<Person> getPerson(UUID personId) {
        return ResponseEntity.of(Optional.of(Person.builder().firstName("Undetermined").lastName("N/A").build()));
    }

    @Override
    public ResponseEntity<Void> createPerson(@Valid Person person) {
        return null;
    }
}
