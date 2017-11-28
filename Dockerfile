FROM openjdk:9-jdk AS build
COPY . /src
WORKDIR /src
RUN mkdir -p /src/mods/jars
RUN mkdir -p /src/mods/compiled


## CLI
##RUN mkdir -p /src/out/cli
##RUN javac -d /src/out/cli \
##    src/cli/com/github/joostvdg/cab/cli/HelloWorld.java \
##    src/cli/module-info.java
##RUN jar --create --file=/src/mods/cab/cli.jar --module-version 1.0 -e com.github.joostvdg.cab.HelloWorld -C /src/out/cli .
#
## scm bitbucket
#RUN mkdir -p /src/out/scm-bitbucket
#RUN javac -d /src/out/scm-bitbucket --module-source-path /src/src -m joostvdg.cab.scm.bitbucket
#RUN jar --create --file=/src/mods/cab/scm-bitbucket.jar -C /src/out/scm-bitbucket .
#
## scm subversion
#RUN mkdir -p /src/out/scm-subversion
#RUN javac -d /src/out/scm-subversion --module-source-path /src/src -m joostvdg.cab.scm.subversion
#RUN jar --create --file=/src/mods/cab/scm-subversion.jar -C /src/out/scm-subversion .
#
#RUN mkdir -p /src/out/cli
#RUN javac -d /src/out/cli --module-source-path /src/src -m joostvdg.cab.cli
#RUN jar --create --file=/src/mods/cab/cli.jar -e com.github.joostvdg.cab.HelloWorld -C /src/out/cli .

RUN javac -d /src/mods/compiled --module-source-path /src/src $(find src -name "*.java")
RUN jar --create --file=/src/mods/jarsjoostvdg.cab.cli.jar --module-version=1.0 -e com.github.joostvdg.cab.cli.HelloWorld -C /src/mods/compiled/joostvdg.cab.cli .
RUN jar --create --file=/src/mods/jars/joostvdg.cab.scm.jar --module-version=1.0 -C /src/mods/compiled/joostvdg.cab.scm .
RUN jar --create --file=/src/mods/jars/joostvdg.cab.product.jar --module-version=1.0 -C /src/mods/compiled/joostvdg.cab.product .
RUN jar --create --file=/src/mods/jars/joostvdg.cab.scm.bitbucket.jar --module-version=1.0 -C /src/mods/compiled/joostvdg.cab.scm.bitbucket .
RUN jar --create --file=/src/mods/jars/joostvdg.cab.scm.subversion.jar --module-version=1.0 -C /src/mods/compiled/joostvdg.cab.scm.subversion .

RUN rm -rf /src/cab-image
RUN jlink --module-path mlib:/$JAVA_HOME/jmods \
    --add-modules joostvdg.cab.cli,joostvdg.cab.scm,joostvdg.cab.scm.subversion,joostvdg.cab.scm.bitbucket \
    --launcher cab=joostvdg.cab.cli \
    --output /src/cab-image

RUN ls -lath /src/cab-image
RUN ls -lath /src/cab-image/bin
RUN /src/cab-image/bin/java --list-modules

FROM debian:stable-slim
ENV DATE_CHANGED="20171111-1235"
COPY --from=build /src/cab-image/ /usr/bin/cab
RUN /usr/bin/cab/bin/java --list-modules
#ENTRYPOINT ["/usr/bin/cli/bin/java", "--list-modules"]
ENTRYPOINT ["/usr/bin/cab/bin/cab"]


