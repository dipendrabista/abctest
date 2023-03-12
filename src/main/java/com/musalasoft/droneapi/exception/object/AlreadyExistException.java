package com.musalasoft.droneapi.exception.object;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@NoArgsConstructor
public class AlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Don't let anyone else instantiate this class
     */
    private AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String message, String objectName) {
        this(String.format(message, objectName));
    }
}
