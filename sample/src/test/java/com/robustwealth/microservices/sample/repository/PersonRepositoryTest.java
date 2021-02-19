package com.robustwealth.microservices.sample.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.robustwealth.microservices.sample.biz.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testFindAll_Success() {
        final Map<UUID, Person> people = personRepository.findAll();

        assertEquals(2, people.size());

        final Person mary = people.get(UUID.fromString("380c8905-c585-40c1-9b9f-8d7c517ba77e"));

        assertNotNull(mary);
        assertEquals("Mary", mary.getFirstName());
        assertEquals("Smith", mary.getLastName());
    }

    @Test
    public void testFindById_Returned() {
        final Person mary = personRepository.findById("380c8905-c585-40c1-9b9f-8d7c517ba77e");

        assertNotNull(mary);
        assertEquals("Mary", mary.getFirstName());
        assertEquals("Smith", mary.getLastName());
    }

    @Test
    public void testFindById_NotReturned() {
        final Person doesNotExist = personRepository.findById("NOT_A_REAL_UUID");

        assertNull(doesNotExist);
    }
}
