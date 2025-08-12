package me.emmy.plugin.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project shark-practice
 * @since 11/08/2025
 */
public class ItemBuilder {
    private final ItemStack itemStack;
    private String command;
    private boolean commandEnabled = true;

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    /**
     * Creates an ItemBuilder with the specified material.
     *
     * @param material the material of the item
     */
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    /**
     * Creates an ItemBuilder with the specified ItemStack.
     *
     * @param itemStack the ItemStack to build upon
     */
    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta == null) {
            meta = this.itemStack.getItemMeta();
        }

        meta.displayName(miniMessage.deserialize("<reset>" + name));
        this.itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder lore(String... lore) {
        List<Component> toSet = new ArrayList<>();
        for (String string : lore) {
            toSet.add(miniMessage.deserialize(string));
        }

        ItemMeta meta = this.itemStack.getItemMeta();
        meta.lore(toSet);
        this.itemStack.setItemMeta(meta);

        return this;
    }

    /**
     * Sets the item to be unbreakable.
     *
     * @param unbreakable true if the item should be unbreakable, false otherwise
     * @return the ItemBuilder instance for method chaining
     */
    public ItemBuilder unbreakable(boolean unbreakable) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta == null) {
            meta = this.itemStack.getItemMeta();
        }

        meta.setUnbreakable(unbreakable);
        this.itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        List<Component> toSet = lore.stream()
                .map(miniMessage::deserialize)
                .collect(Collectors.toList());

        ItemMeta meta = this.itemStack.getItemMeta();
        meta.lore(toSet);
        this.itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder addLore(String line) {
        ItemMeta meta = this.itemStack.getItemMeta();
        List<Component> lore = meta.lore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(miniMessage.deserialize(line));
        meta.lore(lore);

        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment) {
        this.itemStack.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder type(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder clearLore() {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(new ArrayList<>());
        this.itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder glow(boolean glow) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (glow) {
            meta.addEnchant(Enchantment.LURE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.LURE);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        this.itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder clearEnchantments() {
        for (Enchantment e : this.itemStack.getEnchantments().keySet()) {
            this.itemStack.removeEnchantment(e);
        }

        return this;
    }

    public ItemBuilder hideMeta() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.values());
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder command(String command) {
        this.command = command;
        return this;
    }

    public ItemBuilder commandEnabled(boolean enabled) {
        this.commandEnabled = enabled;
        return this;
    }

    public ItemStack build() {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (commandEnabled && command != null) {
            List<Component> lore = meta.lore();

            if (lore == null) {
                lore = new ArrayList<>();
            }

            lore.add(miniMessage.deserialize("<gray>command:" + command));
            meta.lore(lore);
        }

        this.itemStack.setItemMeta(meta);
        return this.itemStack;
    }
}