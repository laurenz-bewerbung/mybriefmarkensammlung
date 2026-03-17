package de.lm.mybriefmarkensammlung.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("Benutzername " + username + " existiert bereits.");
    }
}
