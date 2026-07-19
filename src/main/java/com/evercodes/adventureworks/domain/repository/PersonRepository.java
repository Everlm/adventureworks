package com.evercodes.adventureworks.domain.repository;

import com.evercodes.adventureworks.domain.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<Person> findAll(int limit);

    Optional<Person> findById(Integer id);

    Person save(Person person);

    void deleteById(Integer id);
}
