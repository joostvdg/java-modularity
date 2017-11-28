module joostvdg.cab.resourcebundle.english {
    requires joostvdg.cab.resourcebundle.api;
    provides com.github.joostvdg.cab.resourcebundle.spi.TranslationsProvider
            with com.github.joostvdg.cab.resourcebundle.english.EnglishTranslationsProvider;

}