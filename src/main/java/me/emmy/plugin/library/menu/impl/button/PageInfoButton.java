package me.emmy.plugin.library.menu.impl.button;

import lombok.AllArgsConstructor;
import me.emmy.plugin.library.menu.Button;
import me.emmy.plugin.library.menu.impl.menu.ViewAllPagesMenu;
import me.emmy.plugin.library.menu.pagination.PaginatedMenu;
import me.emmy.plugin.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PageInfoButton extends Button {

    private PaginatedMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        int pages = menu.getPages(player);

        return new ItemBuilder(Material.PAPER)
                .name("&6<bold>Page Info")
                .lore(
                        "&eYou are viewing page &6#" + menu.getPage() + "&e.",
                        "&e" + (pages == 1 ? "There is 1 page." : "There are " + pages + " pages."),
                        "",
                        "&eMiddle click here to",
                        "&eview all pages."
                )
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType == ClickType.RIGHT) {
            new ViewAllPagesMenu(this.menu).openMenu(player);
            playNeutral(player);
        }
    }
}