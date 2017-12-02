package com.github.joostvdg.cab.cli;

import com.github.joostvdg.cab.api.model.ProjectDescriptor;
import com.github.joostvdg.cab.plugin.api.CabPlugin;
import com.github.joostvdg.cab.scm.Encouraged;
import com.github.joostvdg.cab.scm.Scm;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloWorld {
    public static void main(String... args) {

        String localeInput = "nl-NL";
        // String localeInput = args.length > 0 ? args[0] : "en-GB";
        Locale locale = Locale.forLanguageTag(localeInput);

        ResourceBundle translations = ResourceBundle.getBundle("com.github.joostvdg.cab.resourcebundle.Translations", locale);

        System.out.println(translations.getString("welcome"));
        System.out.println("Translation of modularity in " + locale + ": " + translations.getString("modularity_key"));
        System.out.println("Version=0.2.1");
        System.out.println("Lets find our SCM implementations");

        Iterable<Scm> scms = Scm.getScms();
        for (Scm scm : scms) {
            String scmPrintInfo = printScm(scm);
            System.out.println(scmPrintInfo);
        }
        System.out.println("Lets filter for those that are encouraged!");
        ServiceLoader<Scm> scmServices = (ServiceLoader<Scm>)Scm.getScms();
        scmServices.stream()
            .filter(provider-> isEncouraged(provider.type()))
            .map(ServiceLoader.Provider::get)
            .forEach(scm -> System.out.println(printScm(scm)));

        System.out.println("-------------------------------\n\n");
        System.out.println("Listing Modules before loading plugins");
        ModuleManager moduleManager = new ModuleManager();
        moduleManager.printModulesInBootLayer();

        System.out.println("-------------------------------\n\n");

        ProjectDescriptor projectDescriptor = new ProjectDescriptor();
        projectDescriptor.setName("CAB");
        projectDescriptor.setDescription("Some Java Modularity example project");
        projectDescriptor.addAttribute("Hello", "World");

        String pluginDir = args.length > 0 ? args[0] : null;
        if (pluginDir == null) {
            System.exit(0);
        }
        System.out.println("Loading plugins from " + pluginDir);
        ModuleLayer pluginModuleLayer = createPluginLayer(pluginDir);
        ServiceLoader.load(pluginModuleLayer, CabPlugin.class).
            forEach(plugin -> {
                    System.out.println("Invoking " + plugin.name());
                    try {
                        plugin.doWork(projectDescriptor);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            );

        System.out.println("-------------------------------\n\n");
        System.out.println("Listing Modules before loading plugins");
        moduleManager = new ModuleManager();
        moduleManager.printModulesInLayer(pluginModuleLayer);
        System.out.println("-------------------------------\n\n");
    }

    private static ModuleLayer createPluginLayer(String pluginDir) {
        ModuleFinder finder = ModuleFinder.of(Paths.get(pluginDir));

        Set<ModuleReference> pluginModuleRefs = finder.findAll();
        Set<String> pluginRoots = pluginModuleRefs.stream()
            .map(ref -> ref.descriptor().name())
            .collect(Collectors.toSet());

        ModuleLayer parent = ModuleLayer.boot();
        Configuration configuration = parent.configuration().resolve(finder, ModuleFinder.of(), pluginRoots);
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ModuleLayer layer = parent.defineModulesWithOneLoader(configuration, systemClassLoader);
        return layer;
    }

    private static boolean isEncouraged(Class<?> type) {
        return type.isAnnotationPresent(Encouraged.class) && type.getAnnotation(Encouraged.class).value();
    }

    private static String printScm(Scm scm) {
        return new StringBuilder()
                .append("SCM :: ")
                .append(scm.server())
                .append("[Type: ")
                .append(scm.scmType())
                .append(", Package: ")
                .append(scm.getClass().getPackageName())
                .append(", Module: ")
                .append(scm.getClass().getModule())
                .append("]")
                .toString();
    }
}
