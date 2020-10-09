package com.robustwealth.microservices.sample.sampleconsumer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.common.collect.ImmutableList;
import com.robustwealth.microservices.sample.biz.Person;
import com.robustwealth.microservices.sample.controller.SampleControllerRestClient;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MyPeepsController.class)
public class MyPeepsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SampleControllerRestClient sampleControllerClient;

    @Test
    public void testMyPeeps() throws Exception {
        when(sampleControllerClient.getPeople()).thenReturn(ResponseEntity.of(Optional.of(ImmutableList
                .of(Person.builder().id(UUID.randomUUID()).lastName("LastName").firstName("FirstName").build()))));

        mvc.perform(get("/api/people").accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"lastName\":\"LastName\", \"firstName\":\"FirstName\"}]"));
    }
}
