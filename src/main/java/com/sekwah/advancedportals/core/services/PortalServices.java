package com.sekwah.advancedportals.core.services;

import com.google.common.collect.ImmutableList;
import com.sekwah.advancedportals.core.entities.PortalLocation;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.entities.DataTag;
import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.core.entities.containers.PlayerContainer;

import java.util.ArrayList;
import java.util.Map;

/**
 * https://github.com/sekwah41/Advanced-Portals/blob/24175610892152828e21f4ff824eb1589ccb0338/src/com/sekwah/advancedportals/core/api/managers/PortalManager.java
 *
 * Based off the old manager with the data storage and handling moved to {@link IPortalRepository}
 *
 * Note this class's purpose is to hold all of the logic and control what specific access a player has
 * to the data, essentially to stop errors from occurring. It also is an access portal to the API so we can control
 * specifically what they're able to do.
 */
public final class PortalServices {

    public void loadPortals() {

    }

    public boolean inPortalRegion(PlayerLocation loc) {
        return false;
    }

    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return false;
    }

    public ImmutableList<? extends Map.Entry<String, AdvancedPortal>> getPortals() {
        return null;
    }

    public boolean removePortal(String name, PlayerContainer player) {
        return false;
    }

    public AdvancedPortal createPortal(String name, PlayerContainer player, ArrayList<DataTag> portalTags) {
        return null;
    }

    public boolean removePlayerSelection(PlayerContainer player) {
        return false;
    }

    public void playerSelectorActivate(PlayerContainer player, PortalLocation blockLoc, boolean leftClick) {
    }

    public void activateCooldown(PlayerContainer player) {
    }

    public void playerLeave(PlayerContainer player) {
    }
}
