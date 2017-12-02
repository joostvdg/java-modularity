package com.github.joostvdg.cab.api.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectDescriptor {
    private String id;
    private String name;
    private String description;
    private Map<String, String> attributes;

    public ProjectDescriptor() {
        id = UUID.randomUUID().toString();
        attributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public boolean addAttribute(String k, String v) {
        return attributes.put(k, v) != null;
    }
}
