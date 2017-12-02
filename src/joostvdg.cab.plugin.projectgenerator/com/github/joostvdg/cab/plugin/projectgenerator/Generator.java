package com.github.joostvdg.cab.plugin.projectgenerator;

import com.github.joostvdg.cab.api.model.ProjectDescriptor;
import com.github.joostvdg.cab.plugin.api.CabPlugin;

import java.util.Map;

public class Generator implements CabPlugin {

    @Override
    public void doWork(ProjectDescriptor projectDescriptor) throws Exception {
        StringBuilder projectDescriptionBuilder = new StringBuilder();
        projectDescriptionBuilder.append(" > Project: ");
        projectDescriptionBuilder.append(projectDescriptor.getName());
        projectDescriptionBuilder.append("(");
        projectDescriptionBuilder.append(projectDescriptor.getId());
        projectDescriptionBuilder.append(")\n");
        projectDescriptionBuilder.append(" > Description:\n");
        projectDescriptionBuilder.append("  > ");
        projectDescriptionBuilder.append(projectDescriptor.getDescription());
        projectDescriptionBuilder.append("\n");
        projectDescriptionBuilder.append(" > Attributes:\n");
        String attributeListing = listAttributes(projectDescriptor.getAttributes());
        projectDescriptionBuilder.append(attributeListing);
        System.out.println(projectDescriptionBuilder.toString());
    }

    @Override
    public String name() {
        return "ProjectGenerator";
    }

    private String listAttributes(Map<String, String> attributes) {
        StringBuilder attributeListing = new StringBuilder();
        attributes.forEach((k, v) -> {
                attributeListing.append("  > ");
                attributeListing.append(k);
                attributeListing.append(":");
                attributeListing.append(v);
                attributeListing.append("\n");
            }
        );
        return attributeListing.toString();
    }
}
