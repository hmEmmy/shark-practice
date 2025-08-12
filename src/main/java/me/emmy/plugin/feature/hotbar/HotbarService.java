package me.emmy.plugin.feature.hotbar;

import me.emmy.plugin.config.ConfigService;
import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.core.service.annotation.ServiceRegistryPriority;
import me.emmy.plugin.feature.hotbar.enums.HotbarType;
import me.emmy.plugin.feature.hotbar.record.HotbarItem;
import me.emmy.plugin.util.CC;
import me.emmy.plugin.util.ItemBuilder;
import me.emmy.plugin.util.Logger;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author Emmy
 * @project shark-practice
 * @since 11/08/2025
 */
@ServiceRegistryPriority(value = 130)
public class HotbarService implements ServiceRegistryMethodProvider {
    private final Map<HotbarType, List<HotbarItem>> hotbarItems = new HashMap<>();

    @Override
    public void initialize() {
        for (HotbarType type : HotbarType.values()) {
            List<HotbarItem> items = loadItems(type);
            hotbarItems.put(type, items);
        }
    }

    private List<HotbarItem> loadItems(HotbarType hotbarType) {
        FileConfiguration config = this.getPlugin().getService(ConfigService.class).getHotbarConfig();
        List<HotbarItem> items = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection(hotbarType.name());

        if (section == null) {
            Logger.warn("Configuration section '" + hotbarType + "' not found in hotbar.yml.");
            return items;
        }

        for (String key : section.getKeys(false)) {
            try {
                String displayName = section.getString(key + ".display-name");
                String command = section.getString(key + ".command");
                String materialName = Objects.requireNonNull(section.getString(key + ".material"), "Material for hotbar item '" + key + "' is null.");
                Material material = Material.matchMaterial(materialName);

                if (material == null) {
                    Logger.error("Invalid material '" + materialName + "' for hotbar item '" + key + "'. Defaulting to BARRIER.");
                    material = Material.BARRIER;
                }

                int slot = section.getInt(key + ".slot");

                HotbarItem hotbarItem = new HotbarItem(key, displayName, command, material, slot);
                items.add(hotbarItem);
            } catch (Exception exception) {
                Logger.error("Failed to load hotbar item '" + key + "' from section '" + hotbarType + "': " + exception.getMessage());
            }
        }
        return items;
    }

    /**
     * Retrieves the hotbar items for a specific hotbar type.
     *
     * @param hotbarType the type of hotbar (e.g., "LOBBY", "QUEUE", "PARTY", "EVENT", "TOURNAMENT")
     * @return an unmodifiable list of HotbarItem objects for the specified hotbar type
     */
    public List<HotbarItem> getHotbarItems(HotbarType hotbarType) {
        return Collections.unmodifiableList(hotbarItems.getOrDefault(hotbarType, Collections.emptyList()));
    }

    /**
     * Applies the hotbar items to a player's inventory based on the specified hotbar type.
     *
     * @param player     the player whose inventory will be modified
     * @param hotbarType the type of hotbar to apply (e.g., "LOBBY", "QUEUE", "PARTY", "EVENT", "TOURNAMENT")
     */
    public void applyHotbarItems(Player player, HotbarType hotbarType) {
        List<HotbarItem> items = getHotbarItems(hotbarType);
        if (items.isEmpty()) {
            Logger.warn("No hotbar items found for type: " + hotbarType);
            return;
        }

        for (HotbarItem item : items) {
            player.getInventory().setItem(item.slot(), this.toItemStack(item));
        }
        player.updateInventory();
    }

    /**
     * Converts a HotbarItem to an ItemStack.
     *
     * @param item the HotbarItem to convert
     * @return the corresponding ItemStack
     */
    private ItemStack toItemStack(HotbarItem item) {
        return new ItemBuilder(item.material())
                .name(item.displayName())
                .hideMeta()
                .unbreakable(true)
                .build();
    }

    /**
     * Retrieves the HotbarItem corresponding to a given ItemStack.
     *
     * @param itemStack the ItemStack to check
     * @return the HotbarItem if found, otherwise null
     */
    public HotbarItem getHotbarItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir() || !itemStack.hasItemMeta() || itemStack.getItemMeta().displayName() == null) {
            return null;
        }

        Component clickedDisplayName = itemStack.getItemMeta().displayName();
        for (List<HotbarItem> items : hotbarItems.values()) {
            for (HotbarItem item : items) {
                Component storedDisplayName = CC.translate(item.displayName());

                if (item.material() == itemStack.getType() && storedDisplayName.equals(clickedDisplayName)) {
                    return item;
                }
            }
        }
        return null;
    }
}