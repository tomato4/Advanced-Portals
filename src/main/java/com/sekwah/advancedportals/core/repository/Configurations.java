package com.sekwah.advancedportals.core.repository;

import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.entities.Config;
import com.sekwah.advancedportals.core.util.DataHandler;

import java.util.HashMap;

@Singleton
public class Configurations implements IConfigurations {

    private HashMap<String, Config> configs;
    private Config config;

    public Configurations() {
        configs = new HashMap<String,Config>();
    }

    public <T> T getValue(String output) {
        try {
            return (T) configs.get(output);
        } catch (ClassCastException ignored) {

        }
        return null;
    }

    private void test() {
        this.<String>getValue("");
    }

    public boolean getUseOnlySpecialAxe() {
        return this.config.useOnlySpecialAxe;
    }

    public void setUseOnlySpecialAxe(boolean useOnlyServerMadeAxe) {
        this.config.useOnlySpecialAxe = useOnlyServerMadeAxe;
    }

    public String getTranslation() {
        return this.config.translationFile;
    }

    public String getSelectorMaterial() {
        return this.config.selectorMaterial;
    }

    @Override
    public void loadConfig(DataHandler dataStorage) {
        this.config = dataStorage.loadJson(Config.class, "config.json");
    }

}
