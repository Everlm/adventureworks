package com.evercodes.adventureworks.infrastructure.web;

import com.evercodes.adventureworks.application.commons.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResultExtensions {

    public static <T> ResponseEntity<Result<T>> toResponseEntity(Result<T> result) {
        if (result == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return switch (result.getTipo()) {
            case Success -> ResponseEntity.ok(result);
            case NotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            case BadRequest, Invalid, ValidationError -> ResponseEntity.badRequest().body(result);
            case NoContent -> ResponseEntity.noContent().build();
            case Unauthorized -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
            case Forbidden -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
            case Conflict -> ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            case Error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            case ServiceUnavailable -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
        };
    }
}
