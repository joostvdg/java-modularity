import com.github.joostvdg.cab.resourcebundle.spi.TranslationsProvider;

module joostvdg.cab.resourcebundle.dutch {
    requires joostvdg.cab.resourcebundle.api;

    provides TranslationsProvider
            with com.github.joostvdg.cab.resourcebundle.dutch.DutchTranslationsProvider;

}