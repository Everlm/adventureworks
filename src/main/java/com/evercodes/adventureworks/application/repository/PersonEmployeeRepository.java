package com.evercodes.adventureworks.application.repository;

import com.evercodes.adventureworks.application.dto.PersonEmployeeResponse;

import java.util.Optional;

public interface PersonEmployeeRepository 
{

    Optional<PersonEmployeeResponse> findByNationalIdNumber(String nationalIdNumber);
}
