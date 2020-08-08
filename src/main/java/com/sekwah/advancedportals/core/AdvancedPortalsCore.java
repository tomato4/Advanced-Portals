package com.sekwah.advancedportals.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.config.RepositoryModule;
import com.sekwah.advancedportals.core.connector.info.DataCollector;
import com.sekwah.advancedportals.core.util.DataHandler;
import com.sekwah.advancedportals.core.util.TagRegistry;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.util.InfoLogger;

import java.io.File;

public class AdvancedPortalsCore {

    private static AdvancedPortalsCore instance;

    private final int mcMinorVer;
    public String test;
    private final Injector injector = Guice.createInjector(new RepositoryModule());
    private final ILangRepository langRepository;
    private TagRegistry<AdvancedPortal> portalTagRegistry;
    private TagRegistry<Destination> destiTagRegistry;
    private InfoLogger infoLogger;

    private CoreListeners coreListeners = injector.getInstance(CoreListeners.class);


    public static final String version = "1.0.0";
    public static final String lastTranslationUpdate = "1.0.0";

    /**
     * @param dataStorageLoc - Where the files will be located
     * @param infoLogger - The implementation of the logger for the specific platform
     * @param mcVer Minecraft version e.g. 1.12.2
     */
    public AdvancedPortalsCore(File dataStorageLoc, InfoLogger infoLogger,
                               DataCollector dataCollector, int[] mcVer) {

        this.langRepository = injector.getInstance(ILangRepository.class);
        injector.injectMembers(infoLogger);
        this.infoLogger = infoLogger;
        injector.injectMembers(dataCollector);
        injector.injectMembers(new DataHandler(dataStorageLoc));
        infoLogger.log(langRepository.translate("logger.pluginenable"));

        this.mcMinorVer = this.checkMcVer(mcVer);
        this.onEnable();
    }

    private int checkMcVer(int[] mcVer) {
        int maxSupportedVer = 13;
        int minSupportedVer = 13;
        if(mcVer.length == 2 || mcVer.length == 3) {
            if(mcVer[0] == 1) {
                if(mcVer[1] < minSupportedVer) {
                    this.infoLogger.logWarning("Older version of mc detected than officially supported. This is very likely not to work.");
                    return minSupportedVer;
                }
                else if (mcVer[1] > maxSupportedVer) {
                    this.infoLogger.logWarning("Newer version of mc detected than currently supported by this version. The plugin may not work.");
                    return maxSupportedVer;
                }
                else {
                    return mcVer[1];
                }
            }
            else {
                this.infoLogger.logWarning("It seems you are using a very strange version of Minecraft or something is " +
                        "seriously wrong with the plugin for getting the version of Minecraft.");
                return maxSupportedVer;
            }
        }
        else {
            String version = String.valueOf(mcVer[0]);
            for (int i = 0; i < mcVer.length; i++) {
                version += "." + mcVer[i];
            }
            this.infoLogger.logWarning(version + " is definitely not a valid or currently supported mc version. " +
                    "Advanced Portals will try to use the newest available logic and see if it works though results " +
                    "may be unreliable. ");
            return maxSupportedVer;
        }
    }

    private void onEnable() {
        this.portalTagRegistry = new TagRegistry<>();
        this.destiTagRegistry = new TagRegistry<>();
    }

    /**
     * Loads the portal config into the memory and saves from the memory to check in case certain things have changed
     * (basically if values are missing or whatever)
     */
//    public void loadPortalConfig() {
//        this.configRepository.loadConfig(this.dataStorage);
//        this.dataStorage.storeJson(this.configRepository, "config.json");
//    }

    /**
     * This cannot be called to disable the plugin, it just performs any saves or anything needed after if they are required
     */
    public void onDisable() {
        this.infoLogger.log(langRepository.translate("logger.plugindisable"));
    }

    public CoreListeners getCoreListeners() {
        return this.coreListeners;
    }

    public static TagRegistry<AdvancedPortal> getPortalTagRegistry() {
        return instance.portalTagRegistry;
    }

    public static TagRegistry<Destination> getDestinationTagRegistry() {
        return instance.destiTagRegistry;
    }
}
