package com.github.joostvdg.cab.scm.subversion;

import com.github.joostvdg.cab.scm.Scm;
import com.github.joostvdg.cab.scm.ScmType;

public class Subversion implements Scm {

    private String serverUrl;
    private String repositoryPath;
    private String repositoryName;

    public Subversion() {

    }

    /**
     *
     * @param serverUrl
     * @param project
     * @param repositoryName
     */
    public void initialize(final String serverUrl, final String project, final String repositoryName) {
        this.serverUrl = serverUrl;
        this.repositoryPath = project;
        this.repositoryName = repositoryName;
    }

    @Override
    public String server() {
        return "subversion";
    }

    @Override
    public ScmType scmType() {
        return ScmType.SUBVERSION;
    }

    @Override
    public String url() {
        // See https://stackoverflow.com/questions/925423/is-it-better-practice-to-use-string-format-over-string-concatenation-in-java
        // apparently, string builder is faster than string.format and string + string
        return new StringBuilder()
                .append(serverUrl)
                .append("/")
                .append(repositoryPath)
                .append("/")
                .append(repositoryName)
                .toString();
    }
}
