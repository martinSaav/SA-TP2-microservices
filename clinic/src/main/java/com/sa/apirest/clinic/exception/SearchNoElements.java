package com.sa.apirest.clinic.exception;

import org.springframework.http.HttpStatus;

public class SearchNoElements extends BusinessException {

    public SearchNoElements(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
