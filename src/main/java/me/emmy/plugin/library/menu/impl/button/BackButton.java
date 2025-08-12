package me.emmy.plugin.library.menu.impl.button;

import me.emmy.plugin.library.menu.Button;
import me.emmy.plugin.library.menu.Menu;
import me.emmy.plugin.util.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@AllArgsConstructor
public class BackButton extends Button {
    private Menu back;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.ARROW)
                .name("<blue><bold>Go Back")
                .lore(
                        "<dark_gray><strikethrough>----------------------",
                        " <white>This allows you to return",
                        " <white>back to your previous menu",
                        " ",
                        "<green>Click to go back",
                        "<dark_gray><strikethrough>----------------------"
                )
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Button.playNeutral(player);
        back.openMenu(player);
    }
}
