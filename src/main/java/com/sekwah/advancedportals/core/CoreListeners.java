package com.sekwah.advancedportals.core;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.repository.IConfigurations;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.services.PortalTempDataServices;
import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.core.entities.PortalLocation;
import com.sekwah.advancedportals.core.entities.containers.PlayerContainer;
import com.sekwah.advancedportals.core.entities.containers.WorldContainer;


public class CoreListeners {
    @Inject
    ILangRepository langRepository;
    @Inject
    IConfigurations config;
    @Inject
    private PortalServices portalServices;

    public void playerJoin(PlayerContainer player) {
        portalServices.activateCooldown(player);
        if(player.isOp()) {
            if(!langRepository.translate("translatedata.lastchange").equals(AdvancedPortalsCore.lastTranslationUpdate)) {
                player.sendMessage(langRepository.translateColor("messageprefix.negative")
                        + langRepository.translateInsertVariablesColor("translatedata.translationsoutdated", langRepository.getLang()));
                player.sendMessage(langRepository.translateColor("messageprefix.negative")
                        + langRepository.translateColor("translatedata.replacecommand"));
            }
        }
    }

    public void teleportEvent(PlayerContainer player) {
        portalServices.activateCooldown(player);
    }

    public void playerLeave(PlayerContainer player) {
        portalServices.playerLeave(player);
    }

    /**
     * @param loc where the entity spawns
     * @return if the entity is allowed to spawn
     */
    public boolean mobSpawn(PlayerLocation loc) {
        return !this.portalServices.inPortalRegion(loc);
    }

    /**
     * @param player
     * @param fromLoc
     * @param toLoc
     * @return if the player is allowed to move
     */
    public boolean playerMove(PlayerContainer player, PlayerLocation fromLoc, PlayerLocation toLoc) {
        return this.portalServices.playerMove(player, fromLoc, toLoc);
    }

    /**
     *
     * @param fromPos
     * @param toPos
     * @return if movement is allowed
     */
    public boolean liquidFlow(PortalLocation fromPos, PortalLocation toPos) {
        return true;
    }

    /**
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @param blockMaterial
     * @return if the block is allowed to break
     */
    public boolean blockBreak(PlayerContainer player, PortalLocation blockPos, String blockMaterial) {
        return true;
    }

    /**
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @param blockMaterial
     * @return if the block is allowed to be placed
     */
    public boolean blockPlace(PlayerContainer player, PortalLocation blockPos, String blockMaterial, String itemInHandMaterial, String itemInHandName) {
        if(itemInHandName != null && player != null && player.hasPermission("advancedportals.build")) {
            WorldContainer world = player.getWorld();
            if(itemInHandName.equals("\u00A75Portal Block Placer")) {
                world.setBlock(blockPos, "PORTAL");
                return false;
            }
            else if(itemInHandName.equals("\u00A78End Portal Block Placer")) {
                world.setBlock(blockPos, "ENDER_PORTAL");
                return false;
            }
            else if(itemInHandName.equals("\u00A78Gateway Block Placer")) {
                world.setBlock(blockPos, "END_GATEWAY");
                return false;
            }
        }
        return true;
    }

    /**
     * If the block is allowed to be interacted with e.g. a lever
     * @player player causing the event (or null if not a player)
     * @param blockPos
     * @return
     */
    public boolean blockInteract(PlayerContainer player, PortalLocation blockPos) {
        return true;
    }

    /**
     *  @param player
     * @param blockLoc
     * @param leftClick true = left click, false = right click
     * @return if player is allowed to interact with block
     */
    public boolean playerInteractWithBlock(PlayerContainer player, String materialName, String itemName,
                                           PortalLocation blockLoc, boolean leftClick) {
        if(itemName != null && (player.isOp() || player.hasPermission("advancedportals.createportal")) &&
                materialName.equalsIgnoreCase(config.getSelectorMaterial())
                && (!config.getUseOnlySpecialAxe() || itemName.equals("\u00A7ePortal Region Selector"))) {
            portalServices.playerSelectorActivate(player, blockLoc, leftClick);
            return false;
        }
        else if(itemName != null && leftClick && itemName.equals("\u00A75Portal Block Placer") && player.hasPermission("advancedportals.build")) {
            WorldContainer world = player.getWorld();
            if(world.getBlockData(blockLoc) == 1) {
                world.setBlockData(blockLoc, (byte) 2);
            }
            else {
                world.setBlockData(blockLoc, (byte) 1);
            }
        }

        return true;
    }

}
