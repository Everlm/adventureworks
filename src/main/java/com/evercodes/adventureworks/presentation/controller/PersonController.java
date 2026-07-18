package com.evercodes.adventureworks.presentation.controller;

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
    public ResponseEntity<List<PersonResponse>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonResponse> save(@Valid @RequestBody PersonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.save(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
