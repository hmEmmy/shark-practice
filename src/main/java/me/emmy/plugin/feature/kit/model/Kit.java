package me.emmy.plugin.feature.kit.model;

import lombok.Getter;
import lombok.Setter;
import me.emmy.plugin.feature.kit.enums.KitCategory;
import me.emmy.plugin.feature.kit.enums.KitSetting;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@Getter
@Setter
public class Kit {
    private final String name;

    private String description;
    private String disclaimer;

    private String material;

    private boolean enabled;

    private ItemStack[] items;
    private ItemStack[] armor;

    private KitCategory category;

    private EnumSet<KitSetting> settings = EnumSet.noneOf(KitSetting.class);

    /**
     * Constructor for the Kit class.
     *
     * @param name the name of the kit.
     */
    public Kit(String name) {
        this.name = name;

        this.description = name + " kit";
        this.disclaimer = "This is a kit description! (Change me)";

        this.material = "DIAMOND_SWORD";

        this.enabled = false;

        this.items = new ItemStack[36];
        this.armor = new ItemStack[4];

        this.category = KitCategory.REGULAR;
    }

    /**
     * Checks if a specific setting is enabled for this kit.
     *
     * @param setting the KitSetting to check.
     * @return true if the setting is enabled, false otherwise.
     */
    public boolean isSettingEnabled(KitSetting setting) {
        return this.settings.contains(setting);
    }

    /**
     * Toggles a specific setting for this kit.
     *
     * @param setting the KitSetting to toggle.
     * @param enabled true to enable the setting, false to disable it.
     */
    public void toggleSetting(KitSetting setting, boolean enabled) {
        if (enabled) {
            this.settings.add(setting);
        } else {
            this.settings.remove(setting);
        }
    }
}