package com.github.joostvdg.cab.plugin.api;

import com.github.joostvdg.cab.api.model.ProjectDescriptor;

public interface CabPlugin {

    /**
     *
     * @param projectDescriptor
     * @throws Exception
     */
    void doWork(ProjectDescriptor projectDescriptor) throws Exception;

    String name();
}
