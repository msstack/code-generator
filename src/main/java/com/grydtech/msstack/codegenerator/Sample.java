package com.grydtech.msstack.codegenerator;

import com.grydtech.msstack.modelconverter.business.BusinessModel;
import com.grydtech.msstack.modelconverter.engine.ModelConverter;
import com.grydtech.msstack.modelconverter.engine.ModelConverterEntityBased;
import com.grydtech.msstack.modelconverter.engine.ModelReader;
import com.grydtech.msstack.modelconverter.engine.ModelReaderJackson;
import com.grydtech.msstack.modelconverter.microservice.MicroServiceModel;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class Sample {
    private static ModelReader modelReader = new ModelReaderJackson();
    private static ModelConverter modelConverter = new ModelConverterEntityBased();

    public static void main(String[] args) throws URISyntaxException {
        BusinessModel businessModel = readBusinessModelUsingModelConverter();
        List<MicroServiceModel> microServiceModels = modelConverter.convertToMicroServiceModel(businessModel);
        System.out.println("Business model read successfully, version: " + businessModel.getVersion());
    }

    public static BusinessModel readBusinessModelUsingModelConverter() throws URISyntaxException {
        File file = new File(Sample.class.getResource("/sample.json").toURI());
        return modelReader.readBusinessModel(file);
    }
}