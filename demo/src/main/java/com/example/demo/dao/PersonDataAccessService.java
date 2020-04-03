package com.example.demo.dao;

import com.example.demo.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository("postgres")
public class PersonDataAccessService implements PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, @NotNull Person person) {
        final String sql = "INSERT INTO person(id, name) VALUES(uuid_generate_v4(), ?)";

        return jdbcTemplate.update(sql, person.getName());
    }

    @Override
    public List<Person> selectAllPeople() {
        final String sql = "SELECT id, name FROM person";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
    }

    @Override
    public Optional<Person> selectPerson(UUID id) {
        final String sql = "SELECT id, name FROM PERSON WHERE ID = ?";

        Person person = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    UUID personId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    return new Person(personId, name);
                });

        return Optional.ofNullable(person);
    }

    @Override
    public void deletePersonByID(UUID id) {
        final String sql = "DELETE FROM person WHERE id=?";

        jdbcTemplate.update(sql, id);

    }

    @Override
    public int updatePersonById(UUID id, @NotNull Person person) {
        final String sql = "UPDATE person SET name = ? WHERE id = ?";

        return jdbcTemplate.update(sql, person.getName(), id);
    }
}
