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
 * @project shark-practice
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
                "<blue><bold>Kit Commands",
                " <white>▢ <blue>/kit list <white>- <gray>List all available kits",
                " <white>▢ <blue>/kit info <white><name> <dark_gray>| <gray>View information about a kit.",
                " <white>▢ <blue>/kit create <white><name> <dark_gray>| <gray>Create a new kit.",
                " <white>▢ <blue>/kit delete <white><name> <dark_gray>| <gray>Delete an existing kit.",
                " <white>▢ <blue>/kit toggle <white><name> <dark_gray>| <gray>Enable or disable a kit.",
                " <white>▢ <blue>/kit getinv <white><name> <dark_gray>| <gray>Get the inventory of a kit.",
                " <white>▢ <blue>/kit setinv <white><name> <dark_gray>| <gray>Set the inventory for a kit.",
                " <white>▢ <blue>/kit save <white><name> <dark_gray>| <gray>Save the current kit configuration.",
                ""
        ).forEach(line -> sender.sendMessage(CC.translate(line)));
    }

    @Subcommand("list")
    public void onList(CommandSender sender) {
        List<Kit> kits = Shark.getInstance().getService(KitService.class).getKits();

        sender.sendMessage("");
        if (kits.isEmpty()) {
            sender.sendMessage(CC.translate("<red>No kits available."));
        } else {
            sender.sendMessage(CC.translate("<blue><bold>Available Kits:"));
            kits.forEach(kit -> sender.sendMessage(CC.translate(" <gray>▢ <blue>" + kit.getName() + " <dark_gray>- <gray>" + (kit.isEnabled() ? "<green>Enabled" : "<red>Disabled"))));
        }
        sender.sendMessage("");
    }

    @Subcommand("create")
    public void onCreate(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = new Kit(kitName);
        kitService.createKit(kit);

        sender.sendMessage(CC.translate("<green>Kit <blue>" + kitName + " <green>created successfully."));
    }

    @Subcommand("delete")
    public void onDelete(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKits().stream()
                .filter(k -> k.getName().equalsIgnoreCase(kitName))
                .findFirst()
                .orElse(null);

        if (kit == null) {
            sender.sendMessage(CC.translate("<red>Kit <blue>" + kitName + " <red>not found."));
            return;
        }

        kitService.removeKit(kit);
        sender.sendMessage(CC.translate("<green>Kit <blue>" + kitName + " <green>deleted successfully."));
    }

    @Subcommand("toggle")
    public void onToggle(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            sender.sendMessage(CC.translate("<red>Kit <blue>" + kitName + " <red>not found."));
            return;
        }

        kit.setEnabled(!kit.isEnabled());
        kitService.saveKit(kit);

        sender.sendMessage(CC.translate("<green>Kit <blue>" + kitName + " <green>is now " + (kit.isEnabled() ? "<green>enabled" : "<red>disabled") + "."));
    }

    @Subcommand("getinv")
    public void onGetInventory(Player player, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            player.sendMessage(CC.translate("<red>Kit <blue>" + kitName + " <red>not found."));
            return;
        }

        player.getInventory().setContents(kit.getItems());
        player.getInventory().setArmorContents(kit.getArmor());
        player.sendMessage(CC.translate("<white>Successfully retrieved inventory for kit <blue>" + kitName + "<white>."));
    }

    @Subcommand("setinv")
    public void onSetInventory(Player player, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            player.sendMessage(CC.translate("<red>Kit <blue>" + kitName + " <red>not found."));
            return;
        }

        kit.setItems(ItemStackUtil.cloneItemStackArray(player.getInventory().getContents()));
        kit.setArmor(ItemStackUtil.cloneItemStackArray(player.getInventory().getArmorContents()));
        kitService.saveKit(kit);

        player.sendMessage(CC.translate("<white>Successfully set inventory for kit <blue>" + kitName + "<white>."));
    }

    @Subcommand("info")
    public void onInfo(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            sender.sendMessage(CC.translate("<red>Kit <blue>" + kitName + " <red>not found."));
            return;
        }

        Arrays.asList(
                "",
                "<blue><bold>Kit Information",
                " <white>▢ <blue>Name: <white>" + kit.getName(),
                " <white>▢ <blue>Description: <white>" + (kit.getDescription() != null ? kit.getDescription() : "None"),
                " <white>▢ <blue>Disclaimer: <white>" + (kit.getDisclaimer() != null ? kit.getDisclaimer() : "None"),
                " <white>▢ <blue>Material: <white>" + (kit.getMaterial() != null ? kit.getMaterial() : "None"),
                " <white>▢ <blue>Enabled: <white>" + (kit.isEnabled() ? "<green>Yes" : "<red>No"),
                " <white>▢ <blue>Category: <white>" + (kit.getCategory() != null ? kit.getCategory().name() : "None"),
                " <white>▢ <blue>Settings: <white>" + (kit.getSettings() != null && !kit.getSettings().isEmpty() ? kit.getSettings().stream()
                        .map(Enum::name)
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("None") : "None"),
                ""
        ).forEach(line -> sender.sendMessage(CC.translate(line)));
    }

    @Subcommand("save")
    public void onSave(CommandSender sender, String kitName) {
        KitService kitService = Shark.getInstance().getService(KitService.class);
        Kit kit = kitService.getKit(kitName);

        if (kit == null) {
            sender.sendMessage(CC.translate("<red>Kit <blue>" + kitName + " <red>not found."));
            return;
        }

        kitService.saveKit(kit);
        sender.sendMessage(CC.translate("<green>Kit <blue>" + kitName + " <green>saved successfully."));
    }
}