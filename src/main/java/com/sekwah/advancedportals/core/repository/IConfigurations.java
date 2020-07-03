package com.sekwah.advancedportals.core.repository;

import com.sekwah.advancedportals.core.util.DataHandler;

public interface IConfigurations {

    boolean getUseOnlySpecialAxe();

    void setUseOnlySpecialAxe(boolean useOnlyServerMadeAxe);

    String getTranslation();

    String getSelectorMaterial();

    void loadConfig(DataHandler dataStorage);
}
