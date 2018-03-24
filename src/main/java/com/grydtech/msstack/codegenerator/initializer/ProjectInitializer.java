package com.grydtech.msstack.codegenerator.initializer;

import com.google.common.base.CaseFormat;
import com.grydtech.msstack.codegenerator.creator.FileCreator;
import com.grydtech.msstack.codegenerator.creator.FileCreatorFreeMaker;
import com.grydtech.msstack.modelconverter.business.BusinessModel;
import com.grydtech.msstack.modelconverter.engine.ModelConverterEntityBased;
import com.grydtech.msstack.modelconverter.microservice.EntityClassSchema;
import com.grydtech.msstack.modelconverter.microservice.EventClassSchema;
import com.grydtech.msstack.modelconverter.microservice.HandlerClassSchema;
import com.grydtech.msstack.modelconverter.microservice.MicroServiceModel;
import com.grydtech.msstack.modelconverter.engine.ModelConverter;
import com.grydtech.msstack.modelconverter.engine.ModelReader;
import com.grydtech.msstack.modelconverter.engine.ModelReaderJackson;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class ProjectInitializer {
    private final static ModelConverter modelConverter = new ModelConverterEntityBased();
    private final static ModelReader modelReader = new ModelReaderJackson();

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
            String basePackage = groupId + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, artifactId).toLowerCase();

            FileCreator fileCreator = new FileCreatorFreeMaker(new File(projectPath));

            fileCreator.createProjectPom(groupId, artifactId, version);

            for (EntityClassSchema entityClass : microServiceModel.getEntityClasses()) {
                fileCreator.createEntityClass(basePackage, entityClass);
            }

            for (EventClassSchema eventClass : microServiceModel.getEventClasses()) {
                fileCreator.createEventClass(basePackage, eventClass);
            }

            for (HandlerClassSchema handlerClass : microServiceModel.getHandlers()) {
                fileCreator.createHandlerClass(basePackage, handlerClass);
            }
        }
    }
}
