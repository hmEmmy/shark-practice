package me.emmy.plugin.command.impl;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.emmy.plugin.Shark;
import me.emmy.plugin.feature.kit.Kit;
import me.emmy.plugin.feature.kit.KitService;
import me.emmy.plugin.util.CC;
import me.emmy.plugin.util.ItemStackUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * @author Emmy
 * @project Shark
 * @since 10/08/2025
 */
@SuppressWarnings("unused")
@CommandAlias("kit")
@CommandPermission("shark.command.kit")
public class KitCommand extends BaseCommand {

    @Default
    public void onHelp(CommandSender sender) {
        Arrays.asList(
                "",
                "&9&lKit Commands",
                " &f▢ &9/kit list &f- &7List all available kits",
                " &f▢ &9/kit info &f<name> &8| &7View information about a kit.",
                " &f▢ &9/kit create &f<name> &8| &7Create a new kit.",
                " &f▢ &9/kit delete &f<name> &8| &7Delete an existing kit.",
                " &f▢ &9/kit toggle &f<name> &8| &7Enable or disable a kit.",
                " &f▢ &9/kit getinv &f<name> &8| &7Get the inventory of a kit.",
                " &f▢ &9/kit setinv &f<name> &8| &7Set the inventory for a kit.",
                " &f▢ &9/kit save &f<name> &8| &7Save the current kit configuration.",
                ""
        ).forEach(line -> sender.sendMessage(CC.translateLegacy(line)));
    }

    @Subcommand("list")
    public void onList(CommandSender sender) {
        List<Kit> kits = Shark.getInstance().getService(KitService.class).getKits();

        sender.sendMessage("");
        if (kits.isEmpty()) {
            sender.sendMessage(CC.translateLegacy("&cNo kits available."));
        } else {
            sender.sendMessage(CC.translateLegacy("&9&lAvailable Kits:"));
            kits.forEach(kit -> sender.sendMessage(CC.translateLegacy(" &7▢ &9" + kit.getName() + " &8- &7" + (kit.isEnabled() ? "&aEnabled" : "&cDisabled"))));
        }
        sender.sendMessage("");
    }

    @Subcommand("create")
    public void onCreate(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = new Kit(kitName);
        kitService.createKit(kit);

        sender.sendMessage(CC.translateLegacy("&aKit &9" + kitName + " &acreated successfully."));
    }

    @Subcommand("delete")
    public void onDelete(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKits().stream()
                .filter(k -> k.getName().equalsIgnoreCase(kitName))
                .findFirst()
                .orElse(null);

        if (kit == null) {
            sender.sendMessage(CC.translateLegacy("&cKit &9" + kitName + " &cnot found."));
            return;
        }

        kitService.removeKit(kit);
        sender.sendMessage(CC.translateLegacy("&aKit &9" + kitName + " &adeleted successfully."));
    }

    @Subcommand("toggle")
    public void onToggle(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            sender.sendMessage(CC.translateLegacy("&cKit &9" + kitName + " &cnot found."));
            return;
        }

        kit.setEnabled(!kit.isEnabled());
        kitService.saveKit(kit);

        sender.sendMessage(CC.translateLegacy("&aKit &9" + kitName + " &ais now " + (kit.isEnabled() ? "&aenabled" : "&cdisabled") + "."));
    }

    @Subcommand("getinv")
    public void onGetInventory(Player player, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            player.sendMessage(CC.translateLegacy("&cKit &9" + kitName + " &cnot found."));
            return;
        }

        player.getInventory().setContents(kit.getItems());
        player.getInventory().setArmorContents(kit.getArmor());
        player.sendMessage(CC.translateLegacy("&fSuccessfully retrieved inventory for kit &9" + kitName + "&f."));
    }

    @Subcommand("setinv")
    public void onSetInventory(Player player, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            player.sendMessage(CC.translateLegacy("&cKit &9" + kitName + " &cnot found."));
            return;
        }

        kit.setItems(ItemStackUtil.cloneItemStackArray(player.getInventory().getContents()));
        kit.setArmor(ItemStackUtil.cloneItemStackArray(player.getInventory().getArmorContents()));
        kitService.saveKit(kit);

        player.sendMessage(CC.translateLegacy("&fSuccessfully set inventory for kit &9" + kitName + "&f."));
    }

    @Subcommand("info")
    public void onInfo(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            sender.sendMessage(CC.translateLegacy("&cKit &9" + kitName + " &cnot found."));
            return;
        }

        Arrays.asList(
                "",
                "&9&lKit Information",
                " &f▢ &9Name: &f" + kit.getName(),
                " &f▢ &9Description: &f" + (kit.getDescription() != null ? kit.getDescription() : "None"),
                " &f▢ &9Disclaimer: &f" + (kit.getDisclaimer() != null ? kit.getDisclaimer() : "None"),
                " &f▢ &9Material: &f" + (kit.getMaterial() != null ? kit.getMaterial() : "None"),
                " &f▢ &9Enabled: &f" + (kit.isEnabled() ? "&aYes" : "&cNo"),
                " &f▢ &9Category: &f" + (kit.getCategory() != null ? kit.getCategory().name() : "None"),
                " &f▢ &9Settings: &f" + (kit.getSettings() != null && !kit.getSettings().isEmpty() ? kit.getSettings().stream()
                        .map(Enum::name)
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("None") : "None"),
                ""
        ).forEach(line -> sender.sendMessage(CC.translateLegacy(line)));
    }

    @Subcommand("save")
    public void onSave(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            sender.sendMessage(CC.translateLegacy("&cKit &9" + kitName + " &cnot found."));
            return;
        }

        kitService.saveKit(kit);
        sender.sendMessage(CC.translateLegacy("&aKit &9" + kitName + " &asaved successfully."));
    }
}