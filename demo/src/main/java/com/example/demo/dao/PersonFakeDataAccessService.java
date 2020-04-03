package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class PersonFakeDataAccessService implements PersonDao {
    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPerson(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deletePersonByID(UUID id) {
        DB.removeIf(person -> person.getId().equals(id));
    }

    @Override
    public int updatePersonById(UUID id, Person newPerson) {
        return selectPerson(id)
                .map(person -> {
                    int indexOfUpdate = DB.indexOf(person);
                    if (indexOfUpdate >= 0) {
                        DB.set(indexOfUpdate, new Person(id,newPerson.getName()));
                        return 1;
                    }
                    return 0;
                }).orElse(0);
    }
}
