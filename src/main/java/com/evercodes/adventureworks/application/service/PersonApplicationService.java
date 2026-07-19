package com.evercodes.adventureworks.application.service;

import br.com.fluentvalidator.Validator;
import br.com.fluentvalidator.context.ValidationResult;
import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.mapper.PersonMapper;
import com.evercodes.adventureworks.application.validator.PersonValidator;
import com.evercodes.adventureworks.domain.model.BusinessEntity;
import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.domain.repository.BusinessEntityRepository;
import com.evercodes.adventureworks.domain.repository.PersonRepository;
import com.evercodes.adventureworks.presentation.dto.PersonRequest;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonApplicationService {

    private final PersonRepository personRepository;
    private final BusinessEntityRepository businessEntityRepository;
    private final PersonMapper personMapper;
    private final Validator<PersonRequest> personValidator;

    public PersonApplicationService(PersonRepository personRepository,
                                     BusinessEntityRepository businessEntityRepository) {
        this.personRepository = personRepository;
        this.businessEntityRepository = businessEntityRepository;
        this.personMapper = PersonMapper.INSTANCE;
        this.personValidator = new PersonValidator();
    }

    public Result<List<PersonResponse>> findAll() {
        List<PersonResponse> persons = personRepository.findAll().stream()
                .map(personMapper::toResponse)
                .toList();
        return Result.Success(persons, persons.size());
    }

    public Result<PersonResponse> findById(Integer id) {
        return personRepository.findById(id)
                .map(person -> Result.Success(personMapper.toResponse(person)))
                .orElse(Result.NotFound("Person not found with id: " + id));
    }

    @Transactional
    public Result<PersonResponse> save(PersonRequest request) {

        ValidationResult validationResult = personValidator.validate(request);

        if (!validationResult.isValid()) {
            List<String> errors = validationResult.getErrors().stream()
                    .map(error -> error.getField() + ": " + error.getMessage())
                    .collect(Collectors.toList());
            return Result.ValidationError("Validation failed", errors);
        }

        BusinessEntity businessEntity = businessEntityRepository.save(new BusinessEntity());

        Person person = personMapper.toDomain(request);
        person.setBusinessEntityId(businessEntity.getBusinessEntityId());

        Person saved = personRepository.save(person);
        return Result.Success(personMapper.toResponse(saved));
    }

    public Result<Void> deleteById(Integer id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.deleteById(id);
                    return Result.<Void>NoContent("Person deleted successfully");
                })
                .orElse(Result.NotFound("Person not found with id: " + id));
    }

    @Transactional
    public Result<PersonResponse> update(Integer id, PersonRequest request) {

        ValidationResult validationResult = personValidator.validate(request);

        if (!validationResult.isValid()) {
            List<String> errors = validationResult.getErrors().stream()
                    .map(error -> error.getField() + ": " + error.getMessage())
                    .collect(Collectors.toList());
            return Result.ValidationError("Validation failed", errors);
        }

        return personRepository.findById(id)
                .map(person -> {
                    personMapper.updateDomainFromRequest(request, person);
                    Person saved = personRepository.save(person);
                    return Result.Success(personMapper.toResponse(saved));
                })
                .orElse(Result.NotFound("Person not found with id: " + id));
    }
}
