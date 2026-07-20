package com.evercodes.adventureworks.infrastructure.persistence.repository;

import com.evercodes.adventureworks.application.dto.PersonEmployeeResponse;
import com.evercodes.adventureworks.application.repository.PersonEmployeeRepository;
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
        return jpaRepository.findPersonEmployeeByNationalIdNumber(nationalIdNumber);
    }
}
