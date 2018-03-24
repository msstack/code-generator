package com.grydtech.msstack.codegenerator;

import com.grydtech.msstack.codegenerator.initializer.ProjectInitializer;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File input = new File(args[0]);
        File output = new File(args[1]);
        String groupId = args[2];
        String version = args[3];

        try {
            ProjectInitializer.initializeProject(output, input, groupId, version);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
