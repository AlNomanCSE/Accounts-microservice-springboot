package com.noman.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsExpection extends RuntimeException {
    public CustomerAlreadyExistsExpection(String message) {
        super(message);
    }
}
