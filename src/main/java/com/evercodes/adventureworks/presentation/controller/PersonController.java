package com.evercodes.adventureworks.presentation.controller;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.service.PersonApplicationService;
import com.evercodes.adventureworks.infrastructure.web.ResultExtensions;
import com.evercodes.adventureworks.presentation.dto.PersonRequest;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonApplicationService personService;

    public PersonController(PersonApplicationService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<Result<List<PersonResponse>>> findAll() {
        return ResultExtensions.toResponseEntity(personService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<PersonResponse>> findById(@PathVariable Integer id) {
        return ResultExtensions.toResponseEntity(personService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Result<PersonResponse>> save(@Valid @RequestBody PersonRequest request) {
        return ResultExtensions.toResponseEntity(personService.save(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteById(@PathVariable Integer id) {
        return ResultExtensions.toResponseEntity(personService.deleteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<PersonResponse>> update(@PathVariable Integer id,
                                                         @Valid @RequestBody PersonRequest request) {
        return ResultExtensions.toResponseEntity(personService.update(id, request));
    }
}
