package com.cognitree.inject.config;

import com.cognitree.inject.config.ConfigSource;

@ConfigSource("config.yaml")
public class YamlConfig {
    private int intProperty;
    private String stringProperty;
    private boolean booleanProperty;

    public int getIntProperty() {
        return intProperty;
    }

    public void setIntProperty(int intProperty) {
        this.intProperty = intProperty;
    }

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public boolean isBooleanProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(boolean booleanProperty) {
        this.booleanProperty = booleanProperty;
    }
}
