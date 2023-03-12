package com.musalasoft.droneapi.exception.object;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UrlMalformedException extends RuntimeException {

    private UrlMalformedException(String message) {
        super(message);
    }

    public UrlMalformedException(String message, String objectName) {
        this(String.format(message, objectName));
    }
}
