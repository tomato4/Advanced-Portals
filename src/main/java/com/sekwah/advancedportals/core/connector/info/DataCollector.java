package com.sekwah.advancedportals.core.connector.info;

import java.util.List;

/**
 * Gets info from the specific implementation
 * Needs to be renamed
 */
public interface DataCollector {

    boolean materialExists(String materialName);

    List<String> getMaterials();

}
