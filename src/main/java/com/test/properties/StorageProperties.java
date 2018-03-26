package com.test.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "file-storage")
public class StorageProperties {

    @NotNull
    private String location;

    public String getLocation() {
        return "upload-dir";
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
