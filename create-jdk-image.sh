#!/usr/bin/env bash
JAVA_HOME=/home/joost/Tools/jdk-9.0.1
PATH=${JAVA_HOME}/bin:${PATH}
echo " > PWD=${PWD}"
echo " > JAVA_HOME=${JAVA_HOME}"

echo " > Recycling build folders"
rm -rf ${PWD}/mods/compiled
mkdir -p ${PWD}/mods/compiled
rm -rf ${PWD}/mods/jars
mkdir -p ${PWD}/mods/jars
rm -rf ${PWD}/cab-image
mkdir -p ${PWD}/cab-image

echo " > Copying properties files"
cd src
rsync --verbose -Rq $(find . -name *.properties) ../mods/compiled
cd -

echo " > Compiling all classes"
javac -Xlint:unchecked -d ${PWD}/mods/compiled --module-source-path ${PWD}/src/ $(find src -name "*.java")

echo " > Create SCM module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.scm.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.scm .

echo " > Create Product module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.product.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.product .

echo " > Create Bitbucket scm module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.scm.bitbucket.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.scm.bitbucket .

echo " > Create Subversion scm module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.scm.subversion.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.scm.subversion .

echo " > Create ResourceBundle API module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.resourcebundle.api.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.resourcebundle.api .

echo " > Create ResourceBundle Main module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.resourcebundle.english.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.resourcebundle.english .

echo " > Create ResourceBundle Dutch module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.resourcebundle.dutch.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.resourcebundle.dutch .

echo " > Create API module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.api.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.api .

echo " > Create Plugin API module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.pluginapi.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.pluginapi .

echo " > Create Plugin ProjectGenerator module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.plugin.projectgenerator.jar --module-version 1.0 -C ${PWD}/mods/compiled/joostvdg.cab.plugin.projectgenerator .

echo " > Create CLI module"
jar --create --file ${PWD}/mods/jars/joostvdg.cab.cli.jar --module-version 1.0 --module-path ${PWD}/mods/jars/joostvdg.cab.scm.jar -e com.github.joostvdg.cab.cli.HelloWorld -C ${PWD}/mods/compiled/joostvdg.cab.cli .

echo " > Create cab-image"
rm -rf cab-image

echo "---------------------------------------------------------------------------------------------"
jlink --module-path ${PWD}/mods/jars/:/${JAVA_HOME}/jmods \
    --add-modules joostvdg.cab.cli,joostvdg.cab.api,joostvdg.cab.pluginapi \
    --add-modules joostvdg.cab.scm,joostvdg.cab.scm.subversion,joostvdg.cab.scm.bitbucket \
    --add-modules joostvdg.cab.resourcebundle.api,joostvdg.cab.resourcebundle.english,joostvdg.cab.resourcebundle.dutch \
    --launcher cab=joostvdg.cab.cli \
    --output cab-image
echo "---------------------------------------------------------------------------------------------"
echo "==================="
echo "== List Modules "
cab-image/bin/java --list-modules
echo "==================="
echo "==================="
echo "== Running app without plugins"
cab-image/bin/cab
echo "==================="
echo "==================="
echo "== Running app with project generator plugin"
cab-image/bin/cab ${PWD}/mods/compiled/joostvdg.cab.plugin.projectgenerator
echo "==================="
