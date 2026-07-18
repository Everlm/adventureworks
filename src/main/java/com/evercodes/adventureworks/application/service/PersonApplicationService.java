package com.evercodes.adventureworks.application.service;

import com.evercodes.adventureworks.application.dto.Result;
import com.evercodes.adventureworks.domain.model.BusinessEntity;
import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.domain.model.PersonType;
import com.evercodes.adventureworks.domain.repository.BusinessEntityRepository;
import com.evercodes.adventureworks.domain.repository.PersonRepository;
import com.evercodes.adventureworks.presentation.dto.PersonRequest;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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

    public Result<List<PersonResponse>> findAll() {
        List<PersonResponse> persons = personRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
        return Result.success(persons);
    }

    public Result<PersonResponse> findById(Integer id) {
        return personRepository.findById(id)
                .map(person -> Result.success(toResponse(person)))
                .orElse(Result.notFound("Person not found with id: " + id));
    }

    @Transactional
    public Result<PersonResponse> save(PersonRequest request) {
       
        boolean isValid = Arrays.stream(PersonType.values())
                .anyMatch(type -> type == request.getPersonType());

        if (!isValid) {
            String validTypes = Arrays.toString(PersonType.values());
            return Result.error("PersonType must be one of: " + validTypes);
        }

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
        return Result.success(toResponse(saved), "Person created successfully");
    }

    public Result<Void> deleteById(Integer id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.deleteById(id);
                    return Result.<Void>success(null, "Person deleted successfully");
                })
                .orElse(Result.notFound("Person not found with id: " + id));
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
