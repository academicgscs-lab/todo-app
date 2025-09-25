package com.training.todo.infrastructure.persistence.helpers;

import java.util.Collection;

public interface IMarshaller<T> {
    boolean write(Collection<T> list);
}
