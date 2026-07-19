package com.evercodes.adventureworks.application.service;

import com.evercodes.adventureworks.application.dto.CreatePersonCommand;
import com.evercodes.adventureworks.application.dto.Result;
import com.evercodes.adventureworks.application.dto.UpdatePersonCommand;
import com.evercodes.adventureworks.application.mapper.PersonMapper;
import com.evercodes.adventureworks.domain.model.BusinessEntity;
import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.domain.model.PersonType;
import com.evercodes.adventureworks.domain.repository.BusinessEntityRepository;
import com.evercodes.adventureworks.domain.repository.PersonRepository;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class PersonApplicationService {

    private final PersonRepository personRepository;
    private final BusinessEntityRepository businessEntityRepository;
    private final PersonMapper personMapper;

    public PersonApplicationService(PersonRepository personRepository,
                                     BusinessEntityRepository businessEntityRepository) {
        this.personRepository = personRepository;
        this.businessEntityRepository = businessEntityRepository;
        this.personMapper = PersonMapper.INSTANCE;
    }

    public Result<List<PersonResponse>> findAll() {
        List<PersonResponse> persons = personRepository.findAll().stream()
                .map(personMapper::toResponse)
                .toList();
        return Result.success(persons);
    }

    public Result<PersonResponse> findById(Integer id) {
        return personRepository.findById(id)
                .map(person -> Result.success(personMapper.toResponse(person)))
                .orElse(Result.notFound("Person not found with id: " + id));
    }

    @Transactional
    public Result<PersonResponse> save(CreatePersonCommand command) {

        boolean isValid = Arrays.stream(PersonType.values())
                .anyMatch(type -> type == command.getPersonType());

        if (!isValid) {
            String validTypes = Arrays.toString(PersonType.values());
            return Result.error("PersonType must be one of: " + validTypes);
        }

        BusinessEntity businessEntity = businessEntityRepository.save(new BusinessEntity());

        Person person = personMapper.toDomain(command);
        person.setBusinessEntityId(businessEntity.getBusinessEntityId());

        Person saved = personRepository.save(person);
        return Result.success(personMapper.toResponse(saved), "Person created successfully");
    }

    public Result<Void> deleteById(Integer id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.deleteById(id);
                    return Result.<Void>success(null, "Person deleted successfully");
                })
                .orElse(Result.notFound("Person not found with id: " + id));
    }

    @Transactional
    public Result<PersonResponse> update(Integer id, UpdatePersonCommand command) {
        return personRepository.findById(id)
                .map(person -> {
                    personMapper.updateDomainFromCommand(command, person);
                    Person saved = personRepository.save(person);
                    return Result.success(personMapper.toResponse(saved), "Person updated successfully");
                })
                .orElse(Result.notFound("Person not found with id: " + id));
    }
}
