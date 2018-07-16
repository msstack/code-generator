package com.grydtech.msstack.codegenerator.creator;

import com.google.common.base.CaseFormat;
import com.grydtech.msstack.modelconverter.microservice.communication.EventClass;
import com.grydtech.msstack.modelconverter.microservice.communication.RequestClass;
import com.grydtech.msstack.modelconverter.microservice.communication.ResponseClass;
import com.grydtech.msstack.modelconverter.microservice.entity.EntityClass;
import com.grydtech.msstack.modelconverter.microservice.handler.HandlerClass;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public abstract class ProjectMaker {
    protected final String projectPath;
    protected final String groupId;
    protected final String artifactId;
    protected final String version;
    protected final String sourcePath;
    protected final String basePackageName;

    protected ProjectMaker(String projectPath, String groupId, String artifactId, String version) {
        this.projectPath = projectPath;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.sourcePath = String.format("%s/src/main/java", projectPath).replace("/", File.separator);
        this.basePackageName = String.format("%s.%s", groupId, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, artifactId).toLowerCase());
    }

    abstract public void createProjectPom() throws IOException, TemplateException;
    abstract public void createApplicationClass() throws IOException, TemplateException;
    abstract public void createEntityClass(EntityClass entityClass) throws IOException, TemplateException;
    abstract public void createEventClass(EventClass eventClass) throws IOException, TemplateException;
    abstract public void createRequestHandlerClass(HandlerClass handlerClass) throws IOException, TemplateException;
    abstract public void createEventHandlerClass(HandlerClass handlerClass) throws IOException, TemplateException;
    abstract public void createRequestClass(RequestClass requestClass) throws IOException, TemplateException;
    abstract public void createResponseClass(ResponseClass responseClass) throws IOException, TemplateException;
}
