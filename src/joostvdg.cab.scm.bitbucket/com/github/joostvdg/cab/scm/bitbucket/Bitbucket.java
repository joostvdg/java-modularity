package com.github.joostvdg.cab.scm.bitbucket;

import com.github.joostvdg.cab.scm.Encouraged;
import com.github.joostvdg.cab.scm.Scm;
import com.github.joostvdg.cab.scm.ScmType;

@Encouraged
public class Bitbucket implements Scm {

    private String serverUrl;
    private String repositoryPath;
    private String repositoryName;

    public Bitbucket() {

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
        return "bitbucket_server";
    }

    @Override
    public ScmType scmType() {
        return ScmType.GIT;
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
