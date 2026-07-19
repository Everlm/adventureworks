package com.evercodes.adventureworks.presentation.controller;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.service.PersonApplicationService;
import com.evercodes.adventureworks.infrastructure.web.ResultExtensions;
import com.evercodes.adventureworks.presentation.dto.PersonRequest;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Person", description = "Operaciones CRUD de Personas")
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonApplicationService personService;

    public PersonController(PersonApplicationService personService) {
        this.personService = personService;
    }

    @Operation(summary = "findAll", description = "Obtiene una lista de personas")
    @GetMapping
    public ResponseEntity<Result<List<PersonResponse>>> findAll() {
        return ResultExtensions.toResponseEntity(personService.findAll());
    }

    @Operation(summary = "findById", description = "Obtiene una persona por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Result<PersonResponse>> findById(@PathVariable Integer id) {
        return ResultExtensions.toResponseEntity(personService.findById(id));
    }

    @Operation(summary = "save", description = "Crea una nueva persona")
    @PostMapping
    public ResponseEntity<Result<PersonResponse>> save(@RequestBody PersonRequest request) {
        return ResultExtensions.toResponseEntity(personService.save(request));
    }

    @Operation(summary = "deleteById", description = "Elimina una persona por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteById(@PathVariable Integer id) {
        return ResultExtensions.toResponseEntity(personService.deleteById(id));
    }

    @Operation(summary = "update", description = "Actualiza una persona por ID")
    @PutMapping("/{id}")
    public ResponseEntity<Result<PersonResponse>> update(@PathVariable Integer id,
                                                         @RequestBody PersonRequest request) {
        return ResultExtensions.toResponseEntity(personService.update(id, request));
    }
}
