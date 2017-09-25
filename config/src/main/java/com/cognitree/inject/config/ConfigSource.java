package com.cognitree.inject.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * marker interface used by {@link ConfigurationModule } to determine a config bean
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigSource {
    /**
     * name of the configuration file
     */
    String value();
}
