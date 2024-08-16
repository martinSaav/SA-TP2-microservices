package com.sa.apirest.clinic.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentials extends BusinessException {

    InvalidCredentials(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
