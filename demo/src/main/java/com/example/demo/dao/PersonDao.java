package com.example.demo.dao;

import com.example.demo.model.Person;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {
    int insertPerson(UUID id, @NotNull Person person);

    default int insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPerson(UUID id);

    void deletePersonByID(UUID id);

    int updatePersonById(UUID id, @NotNull Person person);
}
