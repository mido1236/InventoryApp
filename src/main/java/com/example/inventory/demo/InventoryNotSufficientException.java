package com.example.inventory.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InventoryNotSufficientException extends RuntimeException {
    public InventoryNotSufficientException() {
        super();
    }

    public InventoryNotSufficientException(String message) {
        super(message);
    }

    public InventoryNotSufficientException(String message, Throwable cause) {
        super(message, cause);
    }
}
