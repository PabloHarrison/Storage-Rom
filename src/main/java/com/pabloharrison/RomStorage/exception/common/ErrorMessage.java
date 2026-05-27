package com.pabloharrison.RomStorage.exception.common;

import org.springframework.http.HttpStatus;

import java.util.Set;

public record ErrorMessage(HttpStatus status, String message, Set<FieldError> errors) {
}
