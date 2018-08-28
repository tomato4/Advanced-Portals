package com.sekwah.advancedportals.listeners;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.portals.Portal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class PortalPlacer implements Listener {

    @SuppressWarnings("unused")
    private final AdvancedPortalsPlugin plugin;
    private final boolean DISABLE_GATEWAY_BEAM;

    // The needed config values will be stored so they are easier to access later
    // an example is in the interact event in this if statement if((!UseOnlyServerAxe || event.getItem().getItemMeta().getDisplayName().equals("�eP...
    private boolean PortalPlace = true;

    public PortalPlacer(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        this.PortalPlace = config.getConfig().getBoolean("CanBuildPortalBlock");

        this.DISABLE_GATEWAY_BEAM = config.getConfig().getBoolean("DisableGatewayBeam", true);

        if (PortalPlace) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getPlayer().hasPermission("advancedportals.build") && event.getItemInHand() != null &&
                event.getItemInHand().hasItemMeta()){
            String name = event.getItemInHand().getItemMeta().getDisplayName();

            if(name == null) return;

            if (name.equals("\u00A75Portal Block Placer")){
                event.getBlockPlaced().setType(Material.PORTAL);
            }
            else if (name.equals("\u00A78End Portal Block Placer")){
                event.getBlockPlaced().setType(Material.ENDER_PORTAL);
            }
            else if (name.equals("\u00A78Gateway Block Placer")){
                event.getBlockPlaced().setType(Material.END_GATEWAY);
                if(this.DISABLE_GATEWAY_BEAM) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        this.plugin.compat.setGatewayAgeHigh(event.getBlock());
                    }, 1);
                }
            }
        }

    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if(!this.DISABLE_GATEWAY_BEAM) {
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            BlockState[] tileEntities = event.getChunk().getTileEntities();
            if(tileEntities.length >0) {
                System.out.println("TILE ENTITIES");
            }
            for(BlockState block : tileEntities) {
                this.plugin.compat.setGatewayAgeHigh(block.getBlock());
            }
        }, 10);
        //event.getHandlers();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        if (material == Material.PORTAL && Portal.inPortalRegion(block.getLocation(), Portal.getPortalProtectionRadius()))
            event.setCancelled(true);
    }
}
