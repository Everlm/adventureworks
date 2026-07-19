package com.evercodes.adventureworks.presentation.controller;

import com.evercodes.adventureworks.application.dto.CreatePersonCommand;
import com.evercodes.adventureworks.application.dto.Result;
import com.evercodes.adventureworks.application.dto.UpdatePersonCommand;
import com.evercodes.adventureworks.application.service.PersonApplicationService;
import com.evercodes.adventureworks.presentation.dto.PersonRequest;
import com.evercodes.adventureworks.presentation.dto.PersonResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        Result<List<PersonResponse>> result = personService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<PersonResponse>> findById(@PathVariable Integer id) {
        Result<PersonResponse> result = personService.findById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping
    public ResponseEntity<Result<PersonResponse>> save(@Valid @RequestBody PersonRequest request) {
        CreatePersonCommand command = new CreatePersonCommand(
                request.getPersonType(),
                request.getTitle(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getSuffix()
        );

        Result<PersonResponse> result = personService.save(command);

        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteById(@PathVariable Integer id) {
        Result<Void> result = personService.deleteById(id);
        if (result.isSuccess()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<PersonResponse>> update(@PathVariable Integer id,
                                                         @Valid @RequestBody PersonRequest request) {
        UpdatePersonCommand command = new UpdatePersonCommand(
                request.getPersonType(),
                request.getTitle(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getSuffix()
        );

        Result<PersonResponse> result = personService.update(id, command);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}
