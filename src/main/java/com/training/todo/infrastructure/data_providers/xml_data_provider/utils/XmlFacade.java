package com.training.todo.infrastructure.data_providers.xml_data_provider.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.nio.file.Path;


public class XmlFacade<T> {

    private Unmarshaller unmarshaller;
    private Marshaller marshaller;

    public XmlFacade(Class<T> item) {
        createContext(item);
    }

    public void createContext(Class<T> targetClass){
        try {
            JAXBContext context = JAXBContext.newInstance(targetClass);
            unmarshaller = context.createUnmarshaller();
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
    }

    public T unmarshal(Path file) throws JAXBException {
        return (T) unmarshaller.unmarshal(file.toFile());
    }

    public void marshall(T model, Path path)  throws JAXBException {
        marshaller.marshal(model, path.toFile());
    }
}
