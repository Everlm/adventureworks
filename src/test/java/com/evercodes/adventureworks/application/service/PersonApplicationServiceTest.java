package com.evercodes.adventureworks.application.service;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.dto.PersonRequest;
import com.evercodes.adventureworks.application.dto.PersonResponse;
import com.evercodes.adventureworks.application.enums.ResultType;
import com.evercodes.adventureworks.application.mapper.PersonMapper;
import com.evercodes.adventureworks.application.validator.PersonValidator;
import com.evercodes.adventureworks.domain.model.BusinessEntity;
import com.evercodes.adventureworks.domain.model.Person;
import com.evercodes.adventureworks.domain.model.PersonType;
import com.evercodes.adventureworks.domain.repository.BusinessEntityRepository;
import com.evercodes.adventureworks.domain.repository.PersonRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonApplicationServiceTest {

    @Test
    void findAllShouldReturnMappedPeople() {
        PersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = businessEntity -> businessEntity;
        PersonMapper personMapper = new InMemoryPersonMapper();
        PersonValidator personValidator = new PersonValidator();

        PersonApplicationService service = new PersonApplicationService(
                personRepository,
                businessEntityRepository,
                personMapper,
                personValidator
        );

        Result<List<PersonResponse>> result = service.findAll();

        assertTrue(result.isEsExito());
        assertEquals(ResultType.Success, result.getTipo());
        assertEquals(1, result.getRegistrosTotales());
        assertEquals("John", result.getDatos().get(0).getFirstName());
    }

    @Test
    void findByIdShouldReturnNotFoundWhenPersonIsMissing() {
        PersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = businessEntity -> businessEntity;
        PersonMapper personMapper = new InMemoryPersonMapper();
        PersonValidator personValidator = new PersonValidator();

        PersonApplicationService service = new PersonApplicationService(
                personRepository,
                businessEntityRepository,
                personMapper,
                personValidator
        );

        Result<PersonResponse> result = service.findById(999);

        assertFalse(result.isEsExito());
        assertEquals(ResultType.NotFound, result.getTipo());
    }

    @Test
    void saveShouldReturnValidationErrorWhenRequestIsInvalid() {
        PersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = businessEntity -> businessEntity;
        PersonMapper personMapper = new InMemoryPersonMapper();
        PersonValidator personValidator = new PersonValidator();

        PersonApplicationService service = new PersonApplicationService(
                personRepository,
                businessEntityRepository,
                personMapper,
                personValidator
        );

        Result<PersonResponse> result = service.save(new PersonRequest());

        assertFalse(result.isEsExito());
        assertEquals(ResultType.ValidationError, result.getTipo());
    }

    @Test
    void saveShouldPersistAndReturnResponseWhenRequestIsValid() {
        InMemoryPersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = businessEntity -> new BusinessEntity(77);
        PersonMapper personMapper = new InMemoryPersonMapper();
        PersonValidator personValidator = new PersonValidator();

        PersonApplicationService service = new PersonApplicationService(
                personRepository,
                businessEntityRepository,
                personMapper,
                personValidator
        );

        PersonRequest request = new PersonRequest(PersonType.EM, null, "Maria", null, "Lopez", null);

        Result<PersonResponse> result = service.save(request);

        assertTrue(result.isEsExito());
        assertEquals(ResultType.Success, result.getTipo());
        assertEquals(77, result.getDatos().getBusinessEntityId());
        assertEquals("Maria", result.getDatos().getFirstName());
        assertEquals(2, personRepository.savedPeople.size());
    }

    @Test
    void updateShouldReturnNoContentWhenPersonExistsAndRequestIsValid() {
        InMemoryPersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = businessEntity -> businessEntity;
        PersonMapper personMapper = new InMemoryPersonMapper();
        PersonValidator personValidator = new PersonValidator();

        PersonApplicationService service = new PersonApplicationService(
                personRepository,
                businessEntityRepository,
                personMapper,
                personValidator
        );

        PersonRequest request = new PersonRequest(PersonType.EM, "Mr.", "John", null, "Updated", null);

        Result<PersonResponse> result = service.update(1, request);

        assertTrue(result.isEsExito());
        assertEquals(ResultType.Success, result.getTipo());
        assertEquals("Updated", result.getDatos().getLastName());
    }

    @Test
    void deleteByIdShouldReturnNoContentWhenPersonExists() {
        InMemoryPersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = businessEntity -> businessEntity;
        PersonMapper personMapper = new InMemoryPersonMapper();
        PersonValidator personValidator = new PersonValidator();

        PersonApplicationService service = new PersonApplicationService(
                personRepository,
                businessEntityRepository,
                personMapper,
                personValidator
        );

        Result<Void> result = service.deleteById(1);

        assertTrue(result.isEsExito());
        assertEquals(ResultType.NoContent, result.getTipo());
        assertEquals(0, personRepository.savedPeople.size());
    }

    private static class InMemoryPersonRepository implements PersonRepository {

        private final List<Person> savedPeople = new ArrayList<>();

        private InMemoryPersonRepository() {
            savedPeople.add(new Person(1, PersonType.EM, false, "Mr.", "John", null, "Doe", null, 0));
        }

        @Override
        public List<Person> findAll(int limit) {
            return new ArrayList<>(savedPeople);
        }

        @Override
        public Optional<Person> findById(Integer id) {
            return savedPeople.stream()
                    .filter(person -> person.getBusinessEntityId().equals(id))
                    .findFirst();
        }

        @Override
        public Person save(Person person) {
            if (person.getBusinessEntityId() == null) {
                person.setBusinessEntityId(77);
            }

            savedPeople.removeIf(existing -> existing.getBusinessEntityId().equals(person.getBusinessEntityId()));
            savedPeople.add(person);
            return person;
        }

        @Override
        public void deleteById(Integer id) {
            savedPeople.removeIf(person -> person.getBusinessEntityId().equals(id));
        }
    }

    private static class InMemoryPersonMapper implements PersonMapper {

        @Override
        public Person toDomain(PersonRequest request) {
            return new Person(null, request.getPersonType(), false, request.getTitle(), request.getFirstName(), request.getMiddleName(), request.getLastName(), request.getSuffix(), 0);
        }

        @Override
        public void updateDomainFromRequest(PersonRequest request, Person person) {
            person.setPersonType(request.getPersonType());
            person.setTitle(request.getTitle());
            person.setFirstName(request.getFirstName());
            person.setMiddleName(request.getMiddleName());
            person.setLastName(request.getLastName());
            person.setSuffix(request.getSuffix());
        }

        @Override
        public PersonResponse toResponse(Person person) {
            return new PersonResponse(
                    person.getBusinessEntityId(),
                    person.getPersonType(),
                    person.getTitle(),
                    person.getFirstName(),
                    person.getMiddleName(),
                    person.getLastName(),
                    person.getSuffix()
            );
        }

        @Override
        public List<PersonResponse> toResponseList(List<Person> persons) {
            return persons.stream().map(this::toResponse).toList();
        }
    }
}