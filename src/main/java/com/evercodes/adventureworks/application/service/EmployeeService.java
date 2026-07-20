package com.evercodes.adventureworks.application.service;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.dto.PersonEmployeeResponse;
import com.evercodes.adventureworks.application.repository.PersonEmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService 
{
    private final PersonEmployeeRepository personEmployeeRepository;

    public EmployeeService(PersonEmployeeRepository personEmployeeRepository) 
    {
        this.personEmployeeRepository = personEmployeeRepository;
    }

    public Result<PersonEmployeeResponse> findByNationalIdNumber(String nationalIdNumber) 
    {
       var employee = personEmployeeRepository.findByNationalIdNumber(nationalIdNumber);

        if (employee.isEmpty()) 
        {
            return Result.NotFound("Employee not found with NationalIDNumber: " + nationalIdNumber);
        }

        return Result.Success(employee.get());
    }
}

