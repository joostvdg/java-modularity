package com.github.joostvdg.cab.scm;

import java.util.ServiceLoader;

public interface Scm {

    /**
     * Retrieves the URL of a repository
     *
     * @return the url
     */
    String url();

    void initialize(final String serverUrl, final String project, final String repositoryName);

    String server();

    ScmType scmType();

    static Iterable<Scm> getScms() {
        return ServiceLoader.load(Scm.class);
    }
}
