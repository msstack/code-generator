package com.grydtech.msstack.codegenerator;

import com.grydtech.msstack.modelconverter.business.BusinessModel;
import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;

public class TestSample {
    @Test
    public void testReadBusinessModelUsingModelConverter() throws URISyntaxException {
        BusinessModel businessModel= Sample.readBusinessModelUsingModelConverter();
        Assert.assertNotNull(businessModel);
        Assert.assertEquals(businessModel.getVersion(), "0.0.1");
    }
}