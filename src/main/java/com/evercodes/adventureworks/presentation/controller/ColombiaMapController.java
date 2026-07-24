package com.evercodes.adventureworks.presentation.controller;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.application.dto.ColombiaMapResponse;
import com.evercodes.adventureworks.application.service.ColombiaMapService;
import com.evercodes.adventureworks.presentation.extension.ResultExtensions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Colombia Maps", description = "Consulta de mapas desde la API externa de Colombia")
@RestController
@RequestMapping("/api/colombia/maps")
public class ColombiaMapController {

    private final ColombiaMapService colombiaMapService;

    public ColombiaMapController(ColombiaMapService colombiaMapService) {
        this.colombiaMapService = colombiaMapService;
    }

    @Operation(summary = "findAll", description = "Obtiene la lista de mapas desde la API externa de Colombia")
    @GetMapping
    public ResponseEntity<Result<List<ColombiaMapResponse>>> findAll() {
        return ResultExtensions.toResponseEntity(colombiaMapService.findAll());
    }
}