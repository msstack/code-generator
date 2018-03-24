package com.grydtech.msstack.codegenerator.creator;

import com.grydtech.msstack.modelconverter.microservice.EntityClassSchema;
import com.grydtech.msstack.modelconverter.microservice.EventClassSchema;
import com.grydtech.msstack.modelconverter.microservice.HandlerClassSchema;
import com.grydtech.msstack.modelconverter.microservice.RequestClassSchema;
import com.grydtech.msstack.modelconverter.microservice.ResponseClassSchema;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface FileCreator {
    void createProjectPom(String groupId, String artifactId, String version) throws IOException, TemplateException;
    void createEntityClass(String packageName, EntityClassSchema entityClass) throws IOException, TemplateException;
    void createEventClass(String packageName, EventClassSchema eventClass) throws IOException, TemplateException;
    void createHandlerClass(String packageName, HandlerClassSchema handlerClass) throws IOException, TemplateException;
    void createRequestClass(String packageName, RequestClassSchema requestClass) throws IOException, TemplateException;
    void createResponseClass(String packageName, ResponseClassSchema responseClass) throws IOException, TemplateException;
}
