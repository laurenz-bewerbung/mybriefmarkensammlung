package de.lm.mybriefmarkensammlung.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(Long id) {
        super("Benutzer mit der id: " + id + " existiert nicht.");
    }

    public NoSuchUserException(String username) {
        super("Benutzer mit dem Benutzernamen " + username + " existiert nicht.");
    }
}
