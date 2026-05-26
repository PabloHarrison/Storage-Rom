package com.pabloharrison.RomStorage.exception;

public class RomNotFoundException extends RuntimeException {
    public RomNotFoundException(String message) {
        super(message);
    }
}
