package com.dailydreams.dailydreams.exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String alreadyExistsMessage) {
        super(alreadyExistsMessage);
    }
}
