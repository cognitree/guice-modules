package com.cognitree.inject.config;

import com.cognitree.inject.config.ConfigSource;

@ConfigSource("config.properties")
public class JavaPropertyConfig {
    private String stringProperty;
    private int intProperty;
    private boolean booleanProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public int getIntProperty() {
        return intProperty;
    }

    public void setIntProperty(int intProperty) {
        this.intProperty = intProperty;
    }

    public boolean isBooleanProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(boolean booleanProperty) {
        this.booleanProperty = booleanProperty;
    }
}
