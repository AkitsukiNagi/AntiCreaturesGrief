package me.akitsuki.antiCreaturesGrief.misc;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.List;

public class Misc {
    final public static List<Material> crops = List.of(
            Material.WHEAT,
            Material.CARROTS,
            Material.POTATOES,
            Material.BEETROOTS,
            Material.BAMBOO,
            Material.SWEET_BERRY_BUSH
    );
    final public static List<EntityType> zombies = List.of(
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.HUSK,
            EntityType.DROWNED
    );
    final public static List<EntityType> skeletons = List.of(
            EntityType.SKELETON,
            EntityType.BOGGED,
            EntityType.STRAY,
            EntityType.WITHER_SKELETON
    );

    public static boolean isRedstoneComponent(Material material) {
        String name = material.name();
        return name.contains("BUTTON") ||
                name.contains("LEVER") ||
                name.contains("TRAPDOOR") ||
                name.contains("DOOR") ||
                name.contains("FENCE_GATE") ||
                material == Material.BELL;
    }
}
