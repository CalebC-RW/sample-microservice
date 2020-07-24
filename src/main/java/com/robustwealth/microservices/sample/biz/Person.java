package com.robustwealth.microservices.sample.biz;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Builder
@Value
public class Person {
    @NotNull
    private UUID id;
    private final String firstName;
    @NotBlank
    private final String lastName;

    @ToString.Exclude
    private final String ssn;
}
