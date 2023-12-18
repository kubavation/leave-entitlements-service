package com.durys.jakub.leaveentitlementsservice.common.exception;

public class DomainValidationException extends RuntimeException {

    public DomainValidationException(String message) {
        super(message);
    }
}
