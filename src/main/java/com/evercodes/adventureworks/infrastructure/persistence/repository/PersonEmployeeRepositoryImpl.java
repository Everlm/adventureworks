package com.evercodes.adventureworks.infrastructure.persistence.repository;

import com.evercodes.adventureworks.application.dto.PersonEmployeeResponse;
import com.evercodes.adventureworks.application.repository.PersonEmployeeRepository;
import com.evercodes.adventureworks.infrastructure.persistence.projection.PersonEmployeeProjection;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersonEmployeeRepositoryImpl implements PersonEmployeeRepository 
{

    private final PersonEmployeeJpaRepository jpaRepository;

    public PersonEmployeeRepositoryImpl(PersonEmployeeJpaRepository jpaRepository) 
    {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<PersonEmployeeResponse> findByNationalIdNumber(String nationalIdNumber) 
    {
        return jpaRepository.findPersonEmployeeByNationalIdNumber(nationalIdNumber)
                .map(this::toResponse);
    }

    private PersonEmployeeResponse toResponse(PersonEmployeeProjection projection) {
        return new PersonEmployeeResponse(
                projection.getFirstName(),
                projection.getLastName(),
                projection.getJobTitle(),
                projection.getHireDate()
        );
    }
}
