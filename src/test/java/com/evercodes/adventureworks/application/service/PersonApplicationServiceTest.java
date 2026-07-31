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

    private PersonApplicationService newService(PersonRepository personRepository,
                                                BusinessEntityRepository businessEntityRepository) {
        return new PersonApplicationService(
                personRepository,
                businessEntityRepository,
                new InMemoryPersonMapper(),
                new PersonValidator()
        );
    }

    @Test
    void findAllShouldReturnMappedPeople() {
        PersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = new InMemoryBusinessEntityRepository();

        PersonApplicationService service = newService(personRepository, businessEntityRepository);

        Result<List<PersonResponse>> result = service.findAll(200);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.Success, result.getType());
        assertEquals(1, result.getTotalRecords());
        assertEquals("John", result.getData().get(0).getFirstName());
    }

    @Test
    void findByIdShouldReturnNotFoundWhenPersonIsMissing() {
        PersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = new InMemoryBusinessEntityRepository();

        PersonApplicationService service = newService(personRepository, businessEntityRepository);

        Result<PersonResponse> result = service.findById(999);

        assertFalse(result.isSuccess());
        assertEquals(ResultType.NotFound, result.getType());
    }

    @Test
    void saveShouldReturnValidationErrorWhenRequestIsInvalid() {
        PersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = new InMemoryBusinessEntityRepository();

        PersonApplicationService service = newService(personRepository, businessEntityRepository);

        Result<PersonResponse> result = service.save(new PersonRequest());

        assertFalse(result.isSuccess());
        assertEquals(ResultType.ValidationError, result.getType());
    }

    @Test
    void saveShouldPersistAndReturnResponseWhenRequestIsValid() {
        InMemoryPersonRepository personRepository = new InMemoryPersonRepository();
        InMemoryBusinessEntityRepository businessEntityRepository = new InMemoryBusinessEntityRepository();

        PersonApplicationService service = newService(personRepository, businessEntityRepository);

        PersonRequest request = new PersonRequest(PersonType.EM, null, "Maria", null, "Lopez", null);

        Result<PersonResponse> result = service.save(request);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.Success, result.getType());
        assertEquals(1, businessEntityRepository.createdEntities.size());
        assertEquals(100, result.getData().getBusinessEntityId());
        assertEquals("Maria", result.getData().getFirstName());
        assertEquals(2, personRepository.savedPeople.size());
    }

    @Test
    void updateShouldReturnSuccessWhenPersonExistsAndRequestIsValid() {
        PersonRepository personRepository = new InMemoryPersonRepository();
        BusinessEntityRepository businessEntityRepository = new InMemoryBusinessEntityRepository();

        PersonApplicationService service = newService(personRepository, businessEntityRepository);

        PersonRequest request = new PersonRequest(PersonType.EM, "Mr.", "John", null, "Updated", null);

        Result<PersonResponse> result = service.update(1, request);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.Success, result.getType());
        assertEquals("Updated", result.getData().getLastName());
    }

    @Test
    void deleteByIdShouldReturnNoContentWhenPersonExists() {
        InMemoryPersonRepository personRepository = new InMemoryPersonRepository();
        InMemoryBusinessEntityRepository businessEntityRepository = new InMemoryBusinessEntityRepository();

        PersonApplicationService service = newService(personRepository, businessEntityRepository);

        Result<Void> result = service.deleteById(1);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.NoContent, result.getType());
        assertEquals(0, personRepository.savedPeople.size());
    }

    @Test
    void deleteByIdShouldDeleteAssociatedBusinessEntity() {
        InMemoryPersonRepository personRepository = new InMemoryPersonRepository();
        InMemoryBusinessEntityRepository businessEntityRepository = new InMemoryBusinessEntityRepository();
        businessEntityRepository.createdEntities.add(new BusinessEntity(1));

        PersonApplicationService service = newService(personRepository, businessEntityRepository);

        Result<Void> result = service.deleteById(1);

        assertTrue(result.isSuccess());
        assertEquals(ResultType.NoContent, result.getType());
        assertEquals(0, personRepository.savedPeople.size());
        assertTrue(businessEntityRepository.deletedIds.contains(1));
    }

    private static class InMemoryBusinessEntityRepository implements BusinessEntityRepository {

        private final List<BusinessEntity> createdEntities = new ArrayList<>();
        private final List<Integer> deletedIds = new ArrayList<>();
        private int nextId = 100;

        @Override
        public BusinessEntity save(BusinessEntity businessEntity) {
            businessEntity.setBusinessEntityId(nextId++);
            createdEntities.add(businessEntity);
            return businessEntity;
        }

        @Override
        public void deleteById(Integer businessEntityId) {
            deletedIds.add(businessEntityId);
            createdEntities.removeIf(entity -> entity.getBusinessEntityId().equals(businessEntityId));
        }
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