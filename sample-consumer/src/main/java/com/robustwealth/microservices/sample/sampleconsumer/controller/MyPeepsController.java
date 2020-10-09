package com.robustwealth.microservices.sample.sampleconsumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robustwealth.microservices.sample.biz.Person;
import com.robustwealth.microservices.sample.controller.SampleControllerRestClient;

@RestController
public class MyPeepsController {
    private final SampleControllerRestClient sampleControllerClient;

    @Autowired
    public MyPeepsController(SampleControllerRestClient sampleControllerClient) {
        super();
        this.sampleControllerClient = sampleControllerClient;
    }

    @GetMapping("/mypeeps")
    public ResponseEntity<List<Person>> getMyPeeps() {
        return sampleControllerClient.getPeople();
    }
}
