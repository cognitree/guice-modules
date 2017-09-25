package com.cognitree.inject.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * guice module to bind all class annotated with {@link ConfigSource} to their respective configuration file.
 */
public class ConfigurationModule extends AbstractModule {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationModule.class);
    private static final ObjectMapper YAML_OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());
    private static final ObjectMapper JAVA_PROPS_OBJECT_MAPPER = new ObjectMapper(new JavaPropsFactory());
    private final String[] packages;

    {
        YAML_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JAVA_PROPS_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @param packages packages to scan for @ConfigSource annotation
     */
    public ConfigurationModule(String... packages) {
        this.packages = packages;
    }

    @Override
    protected void configure() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.forPackages(packages);
        Reflections reflections = new Reflections(configurationBuilder);

        final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ConfigSource.class);
        for (Class<?> aClass : classes) {
            try {
                final Object configuration = loadConfiguration(aClass);
                if (configuration != null)
                    bind((Class) configuration.getClass()).toInstance(configuration);
            } catch (Exception e) {
                logger.error("Exception thrown while loading configuration for class {}", aClass.getName(), e);
            }
        }
    }

    /**
     * loads configuration file specified by {@link ConfigSource } annotation and maps it to the targetClass
     * <p>
     * Note: The type of file is determined by the file extension (.yaml, .yml, .properties)
     */
    private Object loadConfiguration(Class<?> targetClass) throws Exception {

        ConfigSource configSourceAnnotation = (ConfigSource) targetClass.getDeclaredAnnotation(ConfigSource.class);

        final String fileName = configSourceAnnotation.value();

        try (InputStream file = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (file != null) {
                switch (getFileType(fileName)) {
                    case PROPERTIES:
                        logger.info("Loading configuration from properties file {}", fileName);
                        return JAVA_PROPS_OBJECT_MAPPER.readValue(file, targetClass);
                    case YAML:
                        logger.info("Loading configuration from yaml file {}", fileName);
                        return YAML_OBJECT_MAPPER.readValue(file, targetClass);
                }
            }
        } catch (IOException e) {
            logger.error("Couldn't successfully process the YAML/ Properties configuration file {}", fileName, e);
        }

        return null;
    }

    private FileType getFileType(String fileName) throws Exception {
        if (fileName.endsWith(".yml") || fileName.endsWith(".yaml")) {
            return FileType.YAML;
        } else if (fileName.endsWith(".properties")) {
            return FileType.PROPERTIES;
        } else {
            throw new Exception("invalid file type " + fileName);
        }
    }

    private enum FileType {
        PROPERTIES, YAML
    }
}


