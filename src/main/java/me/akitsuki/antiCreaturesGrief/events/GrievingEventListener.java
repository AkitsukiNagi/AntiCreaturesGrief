package me.akitsuki.antiCreaturesGrief.events;

import me.akitsuki.antiCreaturesGrief.AntiCreaturesGrief;
import me.akitsuki.antiCreaturesGrief.misc.Misc;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;

public class GrievingEventListener implements Listener {
    private static FileConfiguration config;
    final private AntiCreaturesGrief plugin;

    public GrievingEventListener(AntiCreaturesGrief plugin) {
        this.plugin = plugin;
        updateConfig();
    }

    public void updateConfig() {
        config = null;
        config = plugin.getConfig();
    }

    @EventHandler
    public void onBlockChangeEvent(EntityChangeBlockEvent event) {
        switch (event.getEntityType()) {
            case ENDERMAN -> {
                if (!config.getBoolean("enderman.block-pickup"))
                    event.setCancelled(true);
            }
            case VILLAGER -> {
                if (!config.getBoolean("villager.allow-farming"))
                    event.setCancelled(true);
            }
            case RAVAGER -> {
                if (!config.getBoolean("ravager.crop-destruction")
                        && Misc.crops.contains(event.getBlock().getType()))
                    event.setCancelled(true);
            }
            case SILVERFISH -> {
                if (!config.getBoolean("silverfish.block-infestation")) {
                    event.setCancelled(true);
                }
            }
            case FOX -> {
                if (!config.getBoolean("fox.berry-harvest")) {
                    event.setCancelled(true);
                }
            }
        }

        if (event.getBlock().getType() == Material.FARMLAND && event.getTo() == Material.DIRT) {
            if (!config.getBoolean(String.format("%s.farm-stepping",
                    event.getEntity().name().toString().toLowerCase()))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        switch  (event.getEntityType()) {
            case CREEPER -> {
                if (!config.getBoolean("creeper.explosion-destruction"))
                    event.blockList().clear();
            }
            case FIREBALL -> {
                if (((Fireball) event.getEntity()).getShooter() instanceof Ghast
                        && !config.getBoolean("ghast.fireball-destruction")) {
                    event.blockList().clear();
                }
            }
            case BREEZE_WIND_CHARGE -> {
                if (!config.getBoolean("breeze.block-interaction")) {
                    event.blockList().clear();
                }
            }
            case WIND_CHARGE -> {
                if (((WindCharge) event.getEntity()).getShooter() instanceof Breeze
                        && !config.getBoolean("breeze.block-interaction")) {
                    event.blockList().clear();
                }
            }
            case WITHER, WITHER_SKULL -> {
                if (!config.getBoolean("wither.block-destruction")) {
                    event.blockList().clear();
                }
            }
            case ENDER_DRAGON, DRAGON_FIREBALL -> {
                if (!config.getBoolean("ender_dragon.block-destruction")) {
                    event.blockList().clear();
                }
            }
        }
    }

    @EventHandler
    public void onIgnition(BlockIgniteEvent event) {
        if (event.getIgnitingEntity() instanceof Fireball
                && ((Fireball) event.getIgnitingEntity()).getShooter() instanceof Ghast
                && !config.getBoolean("ghast.fireball-ignition")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockInteract(EntityInteractEvent event) {
        if ((event.getEntityType() == EntityType.BREEZE_WIND_CHARGE || event.getEntityType() == EntityType.BREEZE)
                && !config.getBoolean("breeze.block-interaction")) {
            event.setCancelled(true);
        }

        switch (event.getBlock().getType()) {
            case FARMLAND -> {
                final String key = "farm-stepping";
                if (Misc.zombies.contains(event.getEntityType())
                    && !config.getBoolean("zombie.")) {
                    event.setCancelled(true);
                } else if (Misc.skeletons.contains(event.getEntityType())
                        && !config.getBoolean(String.format("skeleton.%s", key))) {
                    event.setCancelled(true);
                }

                if (!config.getBoolean(String.format("%s.%s",
                        event.getEntity().name().toString().toLowerCase(), key))) {
                    event.setCancelled(true);
                }
            }
            case TURTLE_EGG -> {
                if (!config.getBoolean("mobs.turtle-egg-trampling")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            Block block = event.getClickedBlock();

            if (block != null && block.getType() == Material.TURTLE_EGG
                    && !config.getBoolean("player.turtle-egg-trampling")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreezeWindChargeHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof BreezeWindCharge
                && !config.getBoolean("breeze.block-interaction")) {
            Block hitBlock = event.getHitBlock();
            if (hitBlock == null) return;

            if (Misc.isRedstoneComponent(hitBlock.getType())) {
                event.setCancelled(true);
                event.getEntity().remove();
            }
        }
    }

    @EventHandler
    public void onDoorBreak(EntityBreakDoorEvent event) {
        String key = "door-breaking";
        if (Misc.zombies.contains(event.getEntityType())
                && !config.getBoolean(String.format("zombie.%s", key))) {
            event.setCancelled(true);
        }

        switch (event.getEntityType()) {
            case VINDICATOR -> {
                if (!config.getBoolean(String.format("vindicator.%s", key))) {
                    event.setCancelled(true);
                }
            }
            case ZOMBIFIED_PIGLIN -> {
                if (!config.getBoolean(String.format("zombified_piglin.%s", key))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        String key = "pickup-items";

        if (Misc.zombies.contains(event.getEntityType())
                && !config.getBoolean(String.format("zombie.%s", key))) {
                event.setCancelled(true);
        }

        switch (event.getEntityType()) {
            case PILLAGER -> {
                if (!config.getBoolean(String.format("pillager.%s", key))) {
                    event.setCancelled(true);
                }
            }
            case PIGLIN -> {
                if (!config.getBoolean(String.format("piglin.%s", key))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWololoEvoke(EntitySpellCastEvent event) {
        if (event.getSpell() == Spellcaster.Spell.WOLOLO
                && !config.getBoolean("evoker.evoke-wololo")) {
            event.setCancelled(true);
        }
    }
}
