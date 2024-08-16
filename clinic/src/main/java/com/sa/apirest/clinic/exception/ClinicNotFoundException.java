package com.sa.apirest.clinic.exception;


import org.springframework.http.HttpStatus;


public class ClinicNotFoundException extends BusinessException {

    public ClinicNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
