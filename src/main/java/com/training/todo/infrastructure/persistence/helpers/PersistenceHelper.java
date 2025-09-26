package com.training.todo.infrastructure.persistence.helpers;

import com.training.todo.infrastructure.persistence.utils.XmlFacade;

public abstract class PersistenceHelper<T, K> implements IMarshaller<T>, ILoader<T> {
    protected final String homePath;
    protected final XmlFacade<K> xml;

    protected PersistenceHelper(String homePath, XmlFacade<K> xmlFacade) {
        this.homePath = homePath;
        this.xml = xmlFacade;
    }
}
