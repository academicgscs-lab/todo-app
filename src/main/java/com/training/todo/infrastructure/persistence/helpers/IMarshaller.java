package com.training.todo.infrastructure.persistence.helpers;

import java.util.Vector;

public interface IMarshaller<T> {
    boolean marshall(Vector<T> list);
}
