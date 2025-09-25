package com.training.todo.infrastructure.persistence.helpers;

import java.util.Optional;
import java.util.Vector;

public interface ILoader<T>{
    Optional<Vector<T>> read();
}
