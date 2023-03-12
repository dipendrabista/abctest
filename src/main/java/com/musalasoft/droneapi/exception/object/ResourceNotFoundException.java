package com.musalasoft.droneapi.exception.object;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Don't let anyone else instantiate this class
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
