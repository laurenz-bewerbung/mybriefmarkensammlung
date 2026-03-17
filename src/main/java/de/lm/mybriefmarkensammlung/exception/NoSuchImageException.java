package de.lm.mybriefmarkensammlung.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchImageException extends RuntimeException {
    public NoSuchImageException(Long id) {
        super("Bild mit der id: " + id + " existiert nicht.");
    }
}
