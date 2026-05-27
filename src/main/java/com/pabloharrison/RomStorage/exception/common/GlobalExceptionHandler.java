package com.pabloharrison.RomStorage.exception.common;

import com.pabloharrison.RomStorage.exception.RomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RomNotFoundException.class)
    public ResponseEntity<ErrorMessage> handlerRomNotFoundException(RomNotFoundException e){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                Set.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Set<FieldError> errors = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toSet());
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                "Validation failed for one or more fields.",
                errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handlerIllegalArgumentException(IllegalArgumentException e){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                Set.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handlerExceptionErrors(Exception e){
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred.",
                Set.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
