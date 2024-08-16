package com.sa.apirest.clinic.exception;

import org.springframework.http.HttpStatus;

public class NotValidBodyException extends BusinessException {

    public NotValidBodyException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
