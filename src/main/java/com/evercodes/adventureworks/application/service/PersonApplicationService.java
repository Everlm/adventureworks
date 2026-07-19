package com.evercodes.adventureworks.application.service;

import br.com.fluentvalidator.Validator;
import lombok.RequiredArgsConstructor;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.mapper.PersonMapper;
import com.evercodes.adventureworks.application.validator.PersonValidator;
import com.evercodes.adventureworks.domain.model.BusinessEntity;
import com.evercodes.adventureworks.domain.repository.BusinessEntityRepository;
import com.evercodes.adventureworks.domain.repository.PersonRepository;
import com.evercodes.adventureworks.presentation.dto.PersonRequest;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
// @RequiredArgsConstructor
public class PersonApplicationService 
{

    private final PersonRepository personRepository;
    private final BusinessEntityRepository businessEntityRepository;
    private final PersonMapper personMapper;
    private final PersonValidator personValidator;

    public PersonApplicationService(PersonRepository personRepository, BusinessEntityRepository businessEntityRepository, PersonMapper personMapper,
        PersonValidator personValidator) 
    {
        this.personRepository = personRepository;
        this.businessEntityRepository = businessEntityRepository;
        this.personMapper = personMapper;
        this.personValidator = personValidator;
    }

    public Result<List<PersonResponse>> findAll() 
    {
        var persons = personRepository.findAll(200);
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
        var validationResult = personValidator.validate(request);

        if (!validationResult.isValid()) {
            
            var errors = validationResult.getErrors().stream()
                    .map(error -> error.getField() + ": " + error.getMessage())
                    .collect(Collectors.toList());

            return Result.ValidationError("Validation failed", errors);
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
        var validationResult = personValidator.validate(request);

        if (!validationResult.isValid()) {
            
            var errors = validationResult.getErrors().stream()
                    .map(error -> error.getField() + ": " + error.getMessage())
                    .collect(Collectors.toList());
            
            return Result.ValidationError("Validation failed", errors);
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

    public Result<Void> deleteById(Integer id) 
    {    
        var person = personRepository.findById(id);

        if (person.isEmpty()) {
            return Result.NotFound("Person not found with id: " + id);
        }

        personRepository.deleteById(id);

        return Result.NoContent("Person deleted successfully");
    }

   
}
