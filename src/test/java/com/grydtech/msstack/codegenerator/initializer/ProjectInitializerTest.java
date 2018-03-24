package com.grydtech.msstack.codegenerator.initializer;

import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class ProjectInitializerTest {
    @Test
    public void testGenerateByInputFile() throws URISyntaxException {
        File input = new File(ProjectInitializer.class.getResource("/sample.json").toURI());
        File output = new File("/home/randil/Documents/Projects/Extra/generated/using-freemaker");
        String groupId = "com.grydtech";
        String version = "0.1.0";

        try {
            ProjectInitializer.initializeProject(output, input, groupId, version);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
