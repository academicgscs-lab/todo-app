package com.training.todo.core.usecases.exceptions;

public class InvalidTaskException extends Exception {
    public InvalidTaskException(String message) {
        super(message);
    }

    public InvalidTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
