package com.grydtech.msstack.codegenerator;

import com.grydtech.msstack.modelconverter.business.BusinessModel;
import com.grydtech.msstack.modelconverter.engine.ModelReaderJackson;

import java.io.File;
import java.net.URISyntaxException;

public class Sample {
    public static void main(String[] args) throws URISyntaxException {
        File file = new File(Sample.class.getResource("/sample.json").toURI());
        BusinessModel businessModel = new ModelReaderJackson().readBusinessModel(file);
        System.out.println("Business model read successfully, version: " + businessModel.getVersion());
    }
}