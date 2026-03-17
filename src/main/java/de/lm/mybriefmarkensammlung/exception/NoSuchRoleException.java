package de.lm.mybriefmarkensammlung.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchRoleException extends RuntimeException {
    public NoSuchRoleException(Long id) {
        super("Rolle mit der id " + id + " existiert nicht.");
    }
}
