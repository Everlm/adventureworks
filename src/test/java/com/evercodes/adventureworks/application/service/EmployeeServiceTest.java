package com.evercodes.adventureworks.application.service;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.dto.PersonEmployeeResponse;
import com.evercodes.adventureworks.application.repository.PersonEmployeeRepository;
import com.evercodes.adventureworks.application.enums.ResultType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeServiceTest {

    @Test
    void findByNationalIdNumberShouldReturnSuccessWhenEmployeeExists() {
        PersonEmployeeRepository repository = nationalIdNumber -> Optional.of(
                new PersonEmployeeResponse("Ana", "Lopez", "Developer", LocalDate.of(2024, 1, 10))
        );

        EmployeeService service = new EmployeeService(repository);

        Result<PersonEmployeeResponse> result = service.findByNationalIdNumber("123");

        assertTrue(result.isEsExito());
        assertEquals(ResultType.Success, result.getTipo());
        assertEquals("Ana", result.getDatos().getFirstName());
        assertEquals("Developer", result.getDatos().getJobTitle());
    }

    @Test
    void findByNationalIdNumberShouldReturnNotFoundWhenEmployeeDoesNotExist() {
        PersonEmployeeRepository repository = nationalIdNumber -> Optional.empty();

        EmployeeService service = new EmployeeService(repository);

        Result<PersonEmployeeResponse> result = service.findByNationalIdNumber("999");

        assertFalse(result.isEsExito());
        assertEquals(ResultType.NotFound, result.getTipo());
        assertEquals("Employee not found with NationalIDNumber: 999", result.getMensajeError());
    }
}