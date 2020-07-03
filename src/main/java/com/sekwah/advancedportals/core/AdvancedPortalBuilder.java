package com.sekwah.advancedportals.core;

import org.bukkit.Bukkit;

import java.io.File;

public class AdvancedPortalBuilder {
    private Bukkit spigot;
    private File dataFolder;

    private static AdvancedPortalBuilder create() {
        return new AdvancedPortalBuilder();
    }
}
