package me.emmy.plugin.library.menu.impl.button;

import lombok.AllArgsConstructor;
import me.emmy.plugin.library.menu.Button;
import me.emmy.plugin.library.menu.pagination.PaginatedMenu;
import me.emmy.plugin.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class JumpToPageButton extends Button {
    private int page;
    private PaginatedMenu menu;
    private boolean current;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.BOOK)
                .name(ChatColor.YELLOW + "Page " + this.page)
                .lore(
                        this.current ? ChatColor.GREEN + "Current page" : "",
                        ChatColor.GRAY + "Click to jump to this page"
                )
                .hideMeta()
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        this.menu.modPage(player, this.page - this.menu.getPage());
        Button.playNeutral(player);
    }

}
