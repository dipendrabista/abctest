package com.musalasoft.droneapi.exception.object;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@AllArgsConstructor
public class OperationFailedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Don't let anyone else instantiate this class
     */
    private OperationFailedException(String message) {
        super(message);
    }

    public OperationFailedException(String message, String objectName) {
        this(String.format(message, objectName));
    }
}
