package com.evercodes.adventureworks.application.service;

import com.evercodes.adventureworks.domain.model.BusinessEntity;
import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.domain.repository.BusinessEntityRepository;
import com.evercodes.adventureworks.domain.repository.PersonRepository;
import com.evercodes.adventureworks.presentation.dto.PersonRequest;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonApplicationService {

    private final PersonRepository personRepository;
    private final BusinessEntityRepository businessEntityRepository;

    public PersonApplicationService(PersonRepository personRepository,
                                     BusinessEntityRepository businessEntityRepository) {
        this.personRepository = personRepository;
        this.businessEntityRepository = businessEntityRepository;
    }

    public List<PersonResponse> findAll() {
        return personRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public PersonResponse findById(Integer id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        return toResponse(person);
    }

    @Transactional
    public PersonResponse save(PersonRequest request) {
        BusinessEntity businessEntity = businessEntityRepository.save(new BusinessEntity());

        Person person = new Person();
        person.setBusinessEntityId(businessEntity.getBusinessEntityId());
        person.setPersonType(request.getPersonType());
        person.setTitle(request.getTitle());
        person.setFirstName(request.getFirstName());
        person.setMiddleName(request.getMiddleName());
        person.setLastName(request.getLastName());
        person.setSuffix(request.getSuffix());

        Person saved = personRepository.save(person);
        return toResponse(saved);
    }

    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }

    private PersonResponse toResponse(Person person) {
        PersonResponse response = new PersonResponse();
        response.setBusinessEntityId(person.getBusinessEntityId());
        response.setPersonType(person.getPersonType());
        response.setTitle(person.getTitle());
        response.setFirstName(person.getFirstName());
        response.setMiddleName(person.getMiddleName());
        response.setLastName(person.getLastName());
        response.setSuffix(person.getSuffix());
        return response;
    }
}
