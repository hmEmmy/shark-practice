package me.emmy.plugin.library.menu.impl.button;

import lombok.AllArgsConstructor;
import me.emmy.plugin.library.menu.Button;
import me.emmy.plugin.library.menu.pagination.PaginatedMenu;
import me.emmy.plugin.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PageButton extends Button {

    private int mod;
    private PaginatedMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        if (this.mod > 0) {
            if (hasNext(player)) {
                return new ItemBuilder(Material.LEVER)
                        .name("<blue><bold>Next Page</blue></bold>")
                        .lore(
                                "<gray><strikethrough>----------------------</strikethrough></gray>",
                                " <white>Page: <blue>" + (this.menu.getPage() + this.mod) + "</blue><white>/<blue>" + this.menu.getPages(player) + "</blue>",
                                " ",
                                "<green>Click to view the next page</green>",
                                "<gray><strikethrough>----------------------</strikethrough></gray>"
                        )
                        .hideMeta()
                        .build();
            } else {
                return new ItemBuilder(Material.LEVER)
                        .name("<blue><bold>Next Page</blue></bold>")
                        .lore(
                                "<gray><strikethrough>----------------------</strikethrough></gray>",
                                " ",
                                "<red>There are no available next pages</red>",
                                "<gray><strikethrough>----------------------</strikethrough></gray>"
                        )
                        .hideMeta()
                        .build();
            }
        } else {
            if (hasPrevious(player)) {
                return new ItemBuilder(Material.LEVER)
                        .name("<blue><bold>Previous Page</blue></bold>")
                        .lore(
                                "<gray><strikethrough>----------------------</strikethrough></gray>",
                                " <white>Page: <blue>" + (this.menu.getPage() + this.mod) + "</blue><white>/<blue>" + this.menu.getPages(player) + "</blue>",
                                " ",
                                "<green>Click to view the previous page</green>",
                                "<gray><strikethrough>----------------------</strikethrough></gray>"
                        )
                        .hideMeta()
                        .build();
            } else {
                return new ItemBuilder(Material.LEVER)
                        .name("<blue><bold>Previous Page</blue></bold>")
                        .lore(
                                "<gray><strikethrough>----------------------</strikethrough></gray>",
                                " ",
                                "<red>There are no available previous pages</red>",
                                "<gray><strikethrough>----------------------</strikethrough></gray>"
                        )
                        .hideMeta()
                        .build();
            }
        }
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (this.mod > 0) {
            if (hasNext(player)) {
                this.menu.modPage(player, this.mod);
                Button.playNeutral(player);
            } else {
                Button.playFail(player);
            }
        } else {
            if (hasPrevious(player)) {
                this.menu.modPage(player, this.mod);
                Button.playNeutral(player);
            } else {
                Button.playFail(player);
            }
        }
    }

    private boolean hasNext(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return this.menu.getPages(player) >= pg;
    }

    private boolean hasPrevious(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return pg > 0;
    }
}