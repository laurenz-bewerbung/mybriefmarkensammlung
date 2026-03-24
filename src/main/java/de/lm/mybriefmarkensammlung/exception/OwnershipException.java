package de.lm.mybriefmarkensammlung.exception;

public class OwnershipException extends RuntimeException {

    public OwnershipException() {
        super("Die angeforderte Ressource gehört einem anderen Nutzer.");
    }
}
