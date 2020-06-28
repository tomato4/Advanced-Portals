package com.sekwah.advancedportals.repository;

import com.sekwah.advancedportals.PlayerLocation;
import com.sekwah.advancedportals.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.UUID;

public interface PortalTempDataRepository {
    void addSelectedPortal(UUID selectedPlayer, String portal);

    void removeSelectedPortal(UUID uuid);

    void addSelectedPosition(UUID uuid, boolean isPos1, PortalLocation portalLocation);

    void removeSelectedPosition(UUID uuid, boolean isPos1);

    void removeAllSelectedHand(UUID uuid);

    void activateCooldown(PlayerContainer player);

    void playerLeave(PlayerContainer player);

    boolean inPortalRegion(PlayerLocation loc);
}