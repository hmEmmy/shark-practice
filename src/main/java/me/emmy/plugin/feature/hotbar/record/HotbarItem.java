package me.emmy.plugin.feature.hotbar.record;

import org.bukkit.Material;

/**
 * @author Emmy
 * @project shark-practice
 * @since 11/08/2025
 */
public record HotbarItem(String name, String displayName, String command, Material material, int slot) {
}