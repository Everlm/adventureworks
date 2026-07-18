package com.evercodes.adventureworks.infrastructure.persistence.repository;

import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.domain.repository.PersonRepository;
import com.evercodes.adventureworks.infrastructure.persistence.entity.PersonEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonJpaRepository jpaRepository;

    public PersonRepositoryImpl(PersonJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Person> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Person save(Person person) {
        PersonEntity entity = toEntity(person);
        PersonEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    private Person toDomain(PersonEntity entity) {
        Person person = new Person();
        person.setBusinessEntityId(entity.getBusinessEntityId());
        person.setPersonType(entity.getPersonType());
        person.setNameStyle(entity.isNameStyle());
        person.setTitle(entity.getTitle());
        person.setFirstName(entity.getFirstName());
        person.setMiddleName(entity.getMiddleName());
        person.setLastName(entity.getLastName());
        person.setSuffix(entity.getSuffix());
        person.setEmailPromotion(entity.getEmailPromotion());
        return person;
    }

    private PersonEntity toEntity(Person person) {
        PersonEntity entity = new PersonEntity();
        entity.setBusinessEntityId(person.getBusinessEntityId());
        entity.setPersonType(person.getPersonType());
        entity.setNameStyle(person.isNameStyle());
        entity.setTitle(person.getTitle());
        entity.setFirstName(person.getFirstName());
        entity.setMiddleName(person.getMiddleName());
        entity.setLastName(person.getLastName());
        entity.setSuffix(person.getSuffix());
        entity.setEmailPromotion(person.getEmailPromotion());
        return entity;
    }
}
