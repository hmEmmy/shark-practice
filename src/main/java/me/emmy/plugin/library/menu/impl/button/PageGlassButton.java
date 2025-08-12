package me.emmy.plugin.library.menu.impl.button;

import me.emmy.plugin.library.menu.Button;
import me.emmy.plugin.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PageGlassButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .build();
    }
}