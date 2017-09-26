/*
 * Copyright 2017 Cognitree Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
