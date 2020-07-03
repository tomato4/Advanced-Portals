package com.sekwah.advancedportals.spigot;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sekwah.advancedportals.core.AdvancedPortalBuilder;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.spigot.coreconnector.command.SpigotCommandRegister;
import com.sekwah.advancedportals.spigot.coreconnector.info.SpigotDataCollector;
import com.sekwah.advancedportals.spigot.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedPortalsPlugin extends JavaPlugin {

    private AdvancedPortalsCore portalsCore;


    /*You cannot inject in this class because the injections aren't being handled until the core class,
    this design needs to be looked at later on as it's sort of a strange way to handle the injection.
     */
    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);

        Pattern p = Pattern.compile("\\(MC:\\s([0-9].[0-9]+.?[0-9]+)\\)");
        Matcher m = p.matcher(Bukkit.getVersion());
        if(m.find()) {
            String version = m.group(1);
            String[] versionNums = version.split("\\.");
            int[] versionInts = new int[versionNums.length];
            for(int i=0;i <versionNums.length; i++) {
                versionInts[i] = Integer.parseInt(versionNums[i]);
            }
            //register commands here
            this.portalsCore = new AdvancedPortalsCore(this.getDataFolder(),
                new SpigotInfoLogger(this), new SpigotDataCollector(), versionInts);
        }
        else {
            this.getLogger().warning("Could not parse mc version from: " + Bukkit.getVersion());
            this.setEnabled(false);
        }
        //injector = Guice.createInjector(new RepositoryModule(this.portalsCore));
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        this.portalsCore.onDisable();
    }

    public AdvancedPortalsCore getPortalsCore() {
        return portalsCore;
    }
}
