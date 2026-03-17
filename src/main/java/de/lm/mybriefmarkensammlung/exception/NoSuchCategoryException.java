package de.lm.mybriefmarkensammlung.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchCategoryException extends RuntimeException {

    public NoSuchCategoryException() {
        super("Kategorie existiert nicht.");
    }

    public NoSuchCategoryException(Long id) {
        super("Kategorie mit der id: " + id + " existiert nicht.");
    }
}
