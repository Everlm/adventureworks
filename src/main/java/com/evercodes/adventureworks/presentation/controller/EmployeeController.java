package com.evercodes.adventureworks.presentation.controller;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.dto.PersonEmployeeResponse;
import com.evercodes.adventureworks.application.service.EmployeeService;
import com.evercodes.adventureworks.presentation.extension.ResultExtensions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employee", description = "Consultas de Empleados")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController 
{
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) 
    {
        this.employeeService = employeeService;
    }

    @Operation(summary = "findByNationalIdNumber", description = "Obtiene datos de Persona y Employee por NationalIDNumber")
    @GetMapping("/national-id/{nationalIdNumber}")
    public ResponseEntity<Result<PersonEmployeeResponse>> findByNationalIdNumber(@PathVariable String nationalIdNumber) 
    {
        return ResultExtensions.toResponseEntity(employeeService.findByNationalIdNumber(nationalIdNumber));
    }
}
