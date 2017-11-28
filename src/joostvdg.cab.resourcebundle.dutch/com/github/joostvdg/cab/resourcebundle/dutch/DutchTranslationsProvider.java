package com.github.joostvdg.cab.resourcebundle.dutch;

import com.github.joostvdg.cab.resourcebundle.spi.TranslationsProvider;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.spi.AbstractResourceBundleProvider;

public class DutchTranslationsProvider extends AbstractResourceBundleProvider implements TranslationsProvider{

    private static Locale DUTCH = Locale.forLanguageTag("nl");

    @Override
    public String toBundleName(String baseName, Locale locale) {
        String bundleName = super.toBundleName(baseName, locale);
        System.out.println("BundleName:" + bundleName);
        if (DUTCH.equals(locale)) {
            int index = bundleName.lastIndexOf('.');
            bundleName = bundleName.substring(0, index + 1) + "dutch" + bundleName.substring(index);
            System.out.println("Locale=DUTCH, bundleName=" + bundleName);
        }
        return bundleName;
    }

    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        if (DUTCH.equals(locale)) {
            return super.getBundle(baseName, locale);
        }
        return null;
    }
}
