package com.evercodes.adventureworks.application.service;

import lombok.RequiredArgsConstructor;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.mapper.PersonMapper;
import com.evercodes.adventureworks.application.validator.PersonValidator;
import com.evercodes.adventureworks.domain.model.BusinessEntity;
import com.evercodes.adventureworks.domain.repository.BusinessEntityRepository;
import com.evercodes.adventureworks.domain.repository.PersonRepository;
import com.evercodes.adventureworks.application.dto.PersonRequest;
import com.evercodes.adventureworks.application.dto.PersonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonApplicationService 
{

    private final PersonRepository personRepository;
    private final BusinessEntityRepository businessEntityRepository;
    private final PersonMapper personMapper;
    private final PersonValidator personValidator;

    public Result<List<PersonResponse>> findAll(int limit) 
    {
        var persons = personRepository.findAll(limit);
        var response = personMapper.toResponseList(persons);
        return Result.Success(response, response.size());
    }

    public Result<PersonResponse> findById(Integer id) 
    {    
        var person = personRepository.findById(id);

        if (person.isEmpty()) {
            return Result.NotFound("Person not found with id: " + id);
        }

        var response = personMapper.toResponse(person.get());
        return Result.Success(response);
    }

    @Transactional
    public Result<PersonResponse> save(PersonRequest request) 
    {
        var validationErrors = validateAndGetErrors(request);

        if (validationErrors.isPresent()) {
            return Result.ValidationError("Validation failed", validationErrors.get());
        }

        var businessEntity = businessEntityRepository.save(new BusinessEntity());

        var person = personMapper.toDomain(request);

        person.setBusinessEntityId(businessEntity.getBusinessEntityId());

        var saved = personRepository.save(person);
        return Result.Success(personMapper.toResponse(saved));
    }

    @Transactional
    public Result<PersonResponse> update(Integer id, PersonRequest request) 
    {
        var validationErrors = validateAndGetErrors(request);

        if (validationErrors.isPresent()) {
            return Result.ValidationError("Validation failed", validationErrors.get());
        }

        var person = personRepository.findById(id);

        if (person.isEmpty()) {
            return Result.NotFound("Person not found with id: " + id);
        }

        personMapper.updateDomainFromRequest(request, person.get());

        var savedPerson = personRepository.save(person.get());
        var response = personMapper.toResponse(savedPerson);

        return Result.Success(response);
    }

    @Transactional
    public Result<Void> deleteById(Integer id) 
    {    
        var person = personRepository.findById(id);

        if (person.isEmpty()) {
            return Result.NotFound("Person not found with id: " + id);
        }

        personRepository.deleteById(id);
        businessEntityRepository.deleteById(person.get().getBusinessEntityId());

        return Result.NoContent("Person deleted successfully");
    }

    private Optional<List<String>> validateAndGetErrors(PersonRequest request) 
    {
        var validationResult = personValidator.validate(request);

        if (validationResult.isValid()) {
            return Optional.empty();
        }

        var errors = validationResult.getErrors().stream()
                .map(error -> error.getField() + ": " + error.getMessage())
                .toList();

        return Optional.of(errors);
    }
}
