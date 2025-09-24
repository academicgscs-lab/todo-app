package com.training.todo.application.utils;

import java.util.UUID;

public class UUIDGenerator {
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
