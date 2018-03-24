package com.grydtech.msstack.codegenerator.creator;

import com.google.common.base.CaseFormat;
import com.grydtech.msstack.codegenerator.creator.templatehelpers.ToCamelCase;
import com.grydtech.msstack.codegenerator.creator.templatehelpers.ToHeadlessCamelCase;
import com.grydtech.msstack.codegenerator.creator.templatehelpers.ToKebabCase;
import com.grydtech.msstack.modelconverter.microservice.Attribute;
import com.grydtech.msstack.modelconverter.microservice.EntityClassSchema;
import com.grydtech.msstack.modelconverter.microservice.EventClassSchema;
import com.grydtech.msstack.modelconverter.microservice.HandlerClassSchema;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCreatorFreeMaker implements FileCreator {
    private final File output;

    private String projectPath;
    private String sourceFilePath;
    private Configuration cfg;

    public FileCreatorFreeMaker(File output) {
        this.output = output;
        initConfigurations();
    }

    private void initConfigurations() {
        try {
            projectPath = output.getCanonicalPath();
            sourceFilePath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java";
            cfg = new Configuration(Configuration.VERSION_2_3_27);
            cfg.setClassForTemplateLoading(getClass(), "/code-templates");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createProjectPom(String groupId, String artifactId, String version) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<>();
        root.put("groupId", groupId);
        root.put("artifactId", artifactId);
        root.put("version", version);
        root.put("toKebab", new ToKebabCase());

        Template template = cfg.getTemplate("pom-file-template.ftl");
        createFile(projectPath, "pom.xml", template, root);
    }

    public void createEntityClass(String basePackageName, EntityClassSchema entityClass) throws IOException, TemplateException {
        String packageName = basePackageName + ".entities";

        List<String> importPackages = new ArrayList<>();

        for (Attribute attribute: entityClass.getAttributes()) {
            if ("array".equals(attribute.getMultiplicity())) {
                importPackages.add("java.util.List");
                break;
            }
        }

        if (!entityClass.getEvents().isEmpty()) {
            importPackages.add(basePackageName + ".events.*");
        }

        Map<String, Object> root = new HashMap<>();
        root.put("packageName", packageName);
        root.put("importPackages", importPackages);
        root.put("className", entityClass.getName());
        root.put("attributes", entityClass.getAttributes());
        root.put("events", entityClass.getEvents());
        root.put("toCamel", new ToCamelCase());
        root.put("toHeadlessCamel", new ToHeadlessCamelCase());

        Template template = cfg.getTemplate("entity-class-template.ftl");
        String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entityClass.getName());
        String packagePath = sourceFilePath + File.separator + packageName.replace(".", File.separator);
        createFile(packagePath, className + ".java", template, root);
    }

    public void createEventClass(String basePackageName, EventClassSchema eventClass) throws IOException, TemplateException {
        String packageName = basePackageName + ".events";

        List<String> importPackages = new ArrayList<>();

        Map<String, Object> root = new HashMap<>();
        root.put("packageName", packageName);
        root.put("importPackages", importPackages);
        root.put("className", eventClass.getName());
        root.put("attributes", eventClass.getAttributes());
        root.put("toCamel", new ToCamelCase());
        root.put("toHeadlessCamel", new ToHeadlessCamelCase());

        Template template = cfg.getTemplate("event-class-template.ftl");
        String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, eventClass.getName());
        String packagePath = sourceFilePath + File.separator + packageName.replace(".", File.separator);
        createFile(packagePath, className + ".java", template, root);
    }

    public void createHandlerClass(String basePackageName, HandlerClassSchema handlerClass) throws IOException, TemplateException {
        String packageName = basePackageName + ".handlers";

        List<String> importPackages = new ArrayList<>();

        importPackages.add(basePackageName + ".events.*");

        Map<String, Object> root = new HashMap<>();
        root.put("packageName", packageName);
        root.put("importPackages", importPackages);
        root.put("className", handlerClass.getName());
        root.put("events", handlerClass.getEvents());
        root.put("toCamel", new ToCamelCase());
        root.put("toHeadlessCamel", new ToHeadlessCamelCase());

        Template template = cfg.getTemplate("handler-class-template.ftl");
        String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, handlerClass.getName());
        String packagePath = sourceFilePath + File.separator + packageName.replace(".", File.separator);
        createFile(packagePath, className + ".java", template, root);
    }

    private void createFile(String path, String fileName, Template template, Map<String, Object> root) throws IOException, TemplateException {
        File filePath = new File(path);
        filePath.mkdirs();

        Writer fileWriter = new java.io.FileWriter(new File(path + File.separator + fileName));
        template.process(root, fileWriter);
    }
}
