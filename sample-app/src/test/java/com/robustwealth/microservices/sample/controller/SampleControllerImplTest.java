package com.robustwealth.microservices.sample.controller;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.robustwealth.microservices.sample.biz.Person;
import com.robustwealth.microservices.sample.service.SampleService;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleControllerImpl.class)
public class SampleControllerImplTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objMapper;

    @MockBean
    private SampleService sampleService;

    @Test
    public void testGetPeople_ZeroReturned() throws Exception {
        new GetPeopleTestPlan() {
            @Override
            protected List<Person> peopleReturnedFromService() {
                return ImmutableList.of();
            }

            @Override
            protected String endpointShouldReturn() {
                return "[]";
            }
        }.performTest();
    }

    @Test
    public void testGetPeople_MultipleReturned() throws Exception {
        new GetPeopleTestPlan() {
            final Person harry = makePerson("Shah", "Harry");
            final Person mary = makePerson("Ringer", "Mary");

            @Override
            protected List<Person> peopleReturnedFromService() {
                return ImmutableList.of(harry, mary);
            }

            @Override
            protected String endpointShouldReturn() {
                return makeJson(ImmutableList.of(harry, mary));
            }
        }.performTest();
    }

    @Test
    public void testGetPeople_ExceptionThrownByService() throws Exception {
        new GetPeopleTestPlan() {
            @Override
            protected void prepareMocks() {
                when(sampleService.getAllPeople()).thenThrow(new RuntimeException("Something went wrong"));
            }

            @Override
            protected ResultMatcher verifyReturnCode(StatusResultMatchers status) {
                return status.isInternalServerError();
            }

            @Override
            protected String endpointShouldReturn() {
                return "{\"message\":\"java.lang.RuntimeException thrown\"}";
            }
        }.performTest();
    }

    // GetPerson Tests
    @Test
    public void testGetPerson_NotReturned() throws Exception {
        new GetPersonTestPlan() {
            @Override
            protected Person personReturnedFromService() {
                return null;
            }

            @Override
            protected String getProvidedPersonId() {
                return UUID.randomUUID().toString();
            }

            @Override
            protected HttpStatus getExpectedStatus() {
                return HttpStatus.NOT_FOUND;
            }
        }.performTest();
    }

    @Test
    public void testGetPerson_PersonReturned() throws Exception {
        final Person harry = makePerson("Shah", "Harry");

        new GetPersonTestPlan() {
            @Override
            protected Person personReturnedFromService() {
                return harry;
            }

            @Override
            protected String getProvidedPersonId() {
                return harry.getId().toString();
            }

            @Override
            protected HttpStatus getExpectedStatus() {
                return HttpStatus.OK;
            }

            @Override
            protected String endpointShouldReturn() {
                return makeJson(harry);
            }
        }.performTest();
    }

    @Test
    public void testGetPerson_InvalidUUID() throws Exception {
        new GetPersonTestPlan() {
            @Override
            protected Person personReturnedFromService() {
                return null;
            }

            @Override
            protected String getProvidedPersonId() {
                return "SOMETHING";
            }

            @Override
            protected HttpStatus getExpectedStatus() {
                return HttpStatus.BAD_REQUEST;
            }
        }.performTest();
    }

    @Test
    public void testGetPerson_ExceptionThrownByService() throws Exception {
        final Person harry = makePerson("Shah", "Harry");

        new GetPersonTestPlan() {
            @Override
            protected String getProvidedPersonId() {
                return harry.getId().toString();
            }

            @Override
            protected void prepareMocks() {
                when(sampleService.getPerson(any(UUID.class))).thenThrow(new RuntimeException("Something went wrong"));
            }

            @Override
            protected HttpStatus getExpectedStatus() {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }.performTest();
    }

    @Test
    public void testCreatePerson_Success() throws Exception {
        final Person harry = makePerson("Shah", "Harry");
        new CreatePersonTestPlan() {

            @Override
            protected Person getProvidedPerson() {
                return harry;
            }

        }.performTest();
    }

    private abstract class GetPeopleTestPlan {
        public void performTest() throws Exception {
            prepareMocks();
            mvc.perform(get("/api/people").accept(MediaType.APPLICATION_JSON)).andExpect(verifyReturnCode(status()))
                    .andExpect(content().contentType(getExpectedContentType()))
                    .andExpect(content().json(endpointShouldReturn()));
        }

        protected abstract String endpointShouldReturn();

        protected List<Person> peopleReturnedFromService() {
            fail("peopleReturnedFromService should be implemented in test");
            return null;
        }

        protected MediaType getExpectedContentType() {
            return MediaType.APPLICATION_JSON;
        }

        protected void prepareMocks() {
            when(sampleService.getAllPeople()).thenReturn(peopleReturnedFromService());
        }

        protected ResultMatcher verifyReturnCode(StatusResultMatchers status) {
            return status.isOk();
        }
    }

    private abstract class GetPersonTestPlan {
        protected UUID PERSON_ID = UUID.randomUUID();

        public void performTest() throws Exception {
            prepareMocks();
            final MvcResult result = mvc
                    .perform(get("/api/people/{personId}", getProvidedPersonId()).accept(MediaType.APPLICATION_JSON))
                    .andReturn();

            verifyResponse(result.getResponse());

        }

        protected void verifyResponse(MockHttpServletResponse response) throws Exception {
            final HttpStatus status = HttpStatus.valueOf(response.getStatus());

            assertEquals(getExpectedStatus(), status);

            if (!status.isError()) {
                JSONAssert.assertEquals(endpointShouldReturn(), response.getContentAsString(), false);
            }
        }

        protected abstract String getProvidedPersonId();

        protected String endpointShouldReturn() {
            fail("Expected return not implemented");
            return null;
        }

        protected Person personReturnedFromService() {
            fail("personReturnedFromService should be implemented in test");
            return null;
        }

        protected void prepareMocks() {
            when(sampleService.getPerson(any(UUID.class))).thenReturn(personReturnedFromService());
        }

        protected HttpStatus getExpectedStatus() {
            return HttpStatus.OK;
        }
    }

    private abstract class CreatePersonTestPlan {
        public void performTest() throws Exception {
            prepareMocks();
            final MvcResult result = mvc
                    .perform(post("/api/people").contentType(MediaType.APPLICATION_JSON).content(getJsonBody()))
                    .andReturn();

            verifyResponse(result.getResponse());

        }

        protected abstract Person getProvidedPerson();

        protected String getJsonBody() {
            return makeJson(getProvidedPerson());
        }

        protected void verifyResponse(MockHttpServletResponse response) throws Exception {
            final HttpStatus status = HttpStatus.valueOf(response.getStatus());

            assertEquals(getExpectedStatus(), status);
        }

        protected void prepareMocks() {
            // Nothing for now
        }

        protected HttpStatus getExpectedStatus() {
            return HttpStatus.OK;
        }
    }

    // Utility methods
    private Person makePerson(String lastName, String firstName) {
        return Person.builder().id(UUID.randomUUID()).lastName(lastName).firstName(firstName).build();
    }

    private String makeJson(Object something) {
        try {
            return objMapper.writeValueAsString(something);
        } catch (final JsonProcessingException ex) {
            ex.printStackTrace();
            fail("JsonProcessingException thrown");
        }

        return null;
    }
}
