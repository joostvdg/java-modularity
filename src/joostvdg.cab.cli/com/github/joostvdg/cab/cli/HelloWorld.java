package com.github.joostvdg.cab.cli;

import com.github.joostvdg.cab.scm.Encouraged;
import com.github.joostvdg.cab.scm.Scm;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ServiceLoader;

public class HelloWorld {
    public static void main(String... args) {

        String localeInput = args.length > 0 ? args[0] : "nl-NL";
//        String localeInput = args.length > 0 ? args[0] : "en-GB";
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
