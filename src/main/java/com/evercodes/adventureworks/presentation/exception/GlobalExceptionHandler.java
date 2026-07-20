package com.evercodes.adventureworks.presentation.exception;

import com.evercodes.adventureworks.application.commons.Result;
import com.evercodes.adventureworks.presentation.extension.ResultExtensions;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        Result<Void> result = Result.ValidationError("Validation failed", errors);
        return ResultExtensions.toResponseEntity(result);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        Result<Void> result = Result.BadRequest(ex.getMessage());
        return ResultExtensions.toResponseEntity(result);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Result<Void>> handleRuntime(RuntimeException ex) {
        Result<Void> result = Result.NotFound(ex.getMessage());
        return ResultExtensions.toResponseEntity(result);
    }
}
