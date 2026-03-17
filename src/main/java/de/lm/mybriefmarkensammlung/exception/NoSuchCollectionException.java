package de.lm.mybriefmarkensammlung.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchCollectionException extends RuntimeException {
    public NoSuchCollectionException(Long id) {
        super("Sammlung mit der id: " + id + " existiert nicht.");
    }
}
