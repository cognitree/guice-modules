package com.cognitree.inject.config;

@ConfigSource("endpoint.yaml")
public class Endpoint {
    public String title;
    public Site site;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
