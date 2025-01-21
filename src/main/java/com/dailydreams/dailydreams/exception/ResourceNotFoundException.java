package com.dailydreams.dailydreams.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String categoryNotFound) {
        super(categoryNotFound);
    }
}
