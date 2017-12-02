module joostvdg.cab.cli {
    requires joostvdg.cab.scm;
    requires joostvdg.cab.product;
    requires joostvdg.cab.resourcebundle.api;

    requires joostvdg.cab.api;
    requires joostvdg.cab.pluginapi;

    uses com.github.joostvdg.cab.resourcebundle.spi.TranslationsProvider;
    uses com.github.joostvdg.cab.plugin.api.CabPlugin;
}
