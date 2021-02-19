package com.robustwealth.microservices.sample.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.robustwealth.microservices.sample.biz.Person;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Person.builder().personId(rs.getString("person_id")).firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name")).build();
    }
}
