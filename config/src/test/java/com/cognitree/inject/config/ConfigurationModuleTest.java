package com.cognitree.inject.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationModuleTest {

    private Injector injector;

    @Before
    public void setup() {
        injector = Guice.createInjector(new ConfigurationModule("com.cognitree"));
        injector.injectMembers(this);
    }

    @Test
    public void testYamlConfig() {
        final YamlConfig yamlConfig = injector.getInstance(YamlConfig.class);
        Assert.assertNotNull(yamlConfig);
        Assert.assertEquals(yamlConfig.getIntProperty(), 1);
        Assert.assertEquals(yamlConfig.getStringProperty(), "stringProperty");
        Assert.assertEquals(yamlConfig.isBooleanProperty(), true);
    }

    @Test
    public void testJavaPropConfig() {
        final JavaPropertyConfig javaPropertyConfig = injector.getInstance(JavaPropertyConfig.class);
        Assert.assertNotNull(javaPropertyConfig);
        Assert.assertEquals(javaPropertyConfig.getIntProperty(), 1);
        Assert.assertEquals(javaPropertyConfig.getStringProperty(), "stringProperty");
        Assert.assertEquals(javaPropertyConfig.isBooleanProperty(), true);
    }

    @Test
    public void testNestedConfig() {
        final Endpoint endpoint = injector.getInstance(Endpoint.class);
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(endpoint.getSite());
        Assert.assertEquals(endpoint.getSite().getHost(), "localhost");
        Assert.assertEquals(endpoint.getSite().getPort(), 8080);
    }
}
