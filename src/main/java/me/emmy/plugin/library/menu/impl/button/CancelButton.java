package me.emmy.plugin.library.menu.impl.button;

import lombok.AllArgsConstructor;
import me.emmy.plugin.library.menu.Button;
import me.emmy.plugin.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Emmy
 * @project Delta
 * @date 26/06/2024 - 23:18
 */
@AllArgsConstructor
public class CancelButton extends Button {
    private final String name;
    private final Material material;
    private final List<String> lore;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(material)
                .name(name)
                .lore(lore)
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType != ClickType.LEFT) return;
        player.closeInventory();
    }
}