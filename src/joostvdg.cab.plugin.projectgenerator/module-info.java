module joostvdg.cab.plugin.projectgenerator {
    requires joostvdg.cab.api;
    requires joostvdg.cab.pluginapi;

    provides com.github.joostvdg.cab.plugin.api.CabPlugin with
        com.github.joostvdg.cab.plugin.projectgenerator.Generator;
}
