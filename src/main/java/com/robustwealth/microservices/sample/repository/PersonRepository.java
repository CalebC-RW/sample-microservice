package com.robustwealth.microservices.sample.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.robustwealth.microservices.sample.biz.Person;
import com.robustwealth.microservices.sample.repository.common.SqlReader;
import com.robustwealth.microservices.sample.repository.mapper.PersonRowMapper;

@Repository
public class PersonRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SqlReader sqlReader;

    @Autowired
    public PersonRepository(DataSource dataSource) {
        super();
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.sqlReader = new SqlReader(PersonRepository.class);
    }

    public Map<UUID, Person> findAll() {
        final String sql = sqlReader.getSql("find-all");
        final List<Person> people = jdbcTemplate.query(sql, new PersonRowMapper());

        LOGGER.info("Find all people returned {} people", people.size());

        return people.stream().collect(Collectors.toMap(Person::getId, person -> person));
    }

    public Person findById(String personId) {
        final String sql = sqlReader.getSql("find-by-id");
        final List<Person> people = jdbcTemplate.query(sql, ImmutableMap.of("personId", personId),
                new PersonRowMapper());

        LOGGER.info("Find person by ID returned {} people for {}", people.size(), personId);

        return people.size() == 1 ? people.get(0) : null;
    }
}
