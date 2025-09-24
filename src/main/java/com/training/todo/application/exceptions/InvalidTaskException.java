package com.training.todo.application.exceptions;

public class InvalidTaskException extends Exception {
    public InvalidTaskException(String message) {
        super(message);
    }
}
