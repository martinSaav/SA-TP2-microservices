package com.sa.apirest.clinic.exception;


import org.springframework.http.HttpStatus;

public class ClinicMismatchException extends BusinessException {

    public ClinicMismatchException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
