package com.evercodes.adventureworks.infrastructure.persistence.repository;

import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.domain.repository.PersonRepository;
import com.evercodes.adventureworks.infrastructure.persistence.entity.PersonEntity;
import com.evercodes.adventureworks.infrastructure.persistence.mapper.PersonEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonJpaRepository jpaRepository;
    private final PersonEntityMapper personEntityMapper;

    public PersonRepositoryImpl(PersonJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
        this.personEntityMapper = PersonEntityMapper.INSTANCE;
    }

    @Override
    public List<Person> findAll(int limit) {
        return jpaRepository.findAll(PageRequest.of(0, limit)).stream()
                .map(personEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return jpaRepository.findById(id).map(personEntityMapper::toDomain);
    }

    @Override
    public Person save(Person person) {
        PersonEntity entity = personEntityMapper.toEntity(person);
        PersonEntity saved = jpaRepository.save(entity);
        return personEntityMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }
}
