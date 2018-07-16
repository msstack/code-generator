package com.grydtech.msstack.codegenerator.initializer;

import com.google.common.base.CaseFormat;
import com.grydtech.msstack.codegenerator.creator.ProjectMaker;
import com.grydtech.msstack.codegenerator.creator.ProjectMakerFreeMaker;
import com.grydtech.msstack.modelconverter.business.BusinessModel;
import com.grydtech.msstack.modelconverter.common.Constants;
import com.grydtech.msstack.modelconverter.microservice.communication.EventClass;
import com.grydtech.msstack.modelconverter.microservice.communication.RequestClass;
import com.grydtech.msstack.modelconverter.microservice.communication.ResponseClass;
import com.grydtech.msstack.modelconverter.microservice.entity.EntityClass;
import com.grydtech.msstack.modelconverter.microservice.handler.HandlerClass;
import com.grydtech.msstack.modelconverter.services.impl.DefaultModelConverter;
import com.grydtech.msstack.modelconverter.microservice.MicroServiceModel;
import com.grydtech.msstack.modelconverter.services.ModelConverter;
import com.grydtech.msstack.modelconverter.services.ModelReader;
import com.grydtech.msstack.modelconverter.services.impl.DefaultModelReader;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class ProjectInitializer {
    private final static ModelConverter modelConverter = new DefaultModelConverter();
    private final static ModelReader modelReader = new DefaultModelReader();

    private ProjectInitializer() {
    }

    public static void initializeProject(File output, File input, String groupId, String version) throws IOException, TemplateException {
        BusinessModel businessModel = modelReader.readBusinessModel(input);
        initializeProject(output, businessModel, groupId, version);
    }

    public static void initializeProject(File output, BusinessModel businessModel, String groupId, String version) throws IOException, TemplateException {
        List<MicroServiceModel> microServiceModels = modelConverter.convertToMicroServiceModel(businessModel);
        initializeProject(output, microServiceModels, groupId, version);
    }

    public static void initializeProject(File output, List<MicroServiceModel> microServiceModels, String groupId, String version) throws IOException, TemplateException {
        for (MicroServiceModel microServiceModel : microServiceModels) {
            String artifactId = microServiceModel.getServiceName();

            String projectPath = output.getCanonicalPath() +
                    File.separator + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, artifactId);

            ProjectMaker projectMaker = new ProjectMakerFreeMaker(projectPath, groupId, artifactId, version);

            projectMaker.createProjectPom();
            projectMaker.createApplicationClass();

            for (EntityClass entityClass : microServiceModel.getEntityClasses()) {
                projectMaker.createEntityClass(entityClass);
            }

            for (EventClass eventClass : microServiceModel.getEventClasses()) {
                projectMaker.createEventClass(eventClass);
            }

            for (HandlerClass handlerClass : microServiceModel.getHandlerClasses()) {
                if (handlerClass.getType().equals(Constants.EVENT_HANDLER_TYPE)) {
                    projectMaker.createEventHandlerClass(handlerClass);
                } else {
                    projectMaker.createRequestHandlerClass(handlerClass);
                }
            }

            for (RequestClass requestClass : microServiceModel.getRequestClasses()) {
                projectMaker.createRequestClass(requestClass);
            }

            for (ResponseClass responseClass : microServiceModel.getResponseClasses()) {
                projectMaker.createResponseClass(responseClass);
            }
        }
    }
}
