package com.afterkraft.kraftrpg.api;

public class LateRegistrationException extends RuntimeException {
    private static final long serialVersionUID = -3147116935352263037L;

    public LateRegistrationException(String message) {
        super(message);
    }
}
