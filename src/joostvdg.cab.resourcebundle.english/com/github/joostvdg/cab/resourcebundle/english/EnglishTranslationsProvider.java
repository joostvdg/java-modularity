package com.github.joostvdg.cab.resourcebundle.english;

import com.github.joostvdg.cab.resourcebundle.spi.TranslationsProvider;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.spi.AbstractResourceBundleProvider;

public class EnglishTranslationsProvider extends AbstractResourceBundleProvider implements TranslationsProvider {

    private static Locale ENGLISH = Locale.forLanguageTag("en");

    @Override
    public String toBundleName(String baseName, Locale locale) {
        String bundleName = super.toBundleName(baseName, locale);
        if (ENGLISH.equals(locale)) {
            int index = bundleName.lastIndexOf('.');
            return bundleName.substring(0, index + 1) + bundleName.substring(index);
            // return bundleName.substring(0, index + 1) + "english" + bundleName.substring(index);
        }
        return bundleName;
    }

    @Override
    public ResourceBundle getBundle(String baseName, Locale locale) {
        if (ENGLISH.equals(locale)) {
            return super.getBundle(baseName, locale);
        }
        return null;
    }
}
