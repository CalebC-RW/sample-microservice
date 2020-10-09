package com.robustwealth.microservices.sample.biz;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Builder
@Value
public class Person {
    private UUID id;
    private String firstName;
    @NotBlank
    private String lastName;

    @ToString.Exclude
    private String ssn;

    public static class PersonBuilder {
        public PersonBuilder personId(String personIdAsString) {
            return id(UUID.fromString(personIdAsString));
        }
    }
}
