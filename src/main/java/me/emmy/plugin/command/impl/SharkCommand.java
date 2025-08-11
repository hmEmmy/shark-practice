package me.emmy.plugin.command.impl;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.emmy.plugin.Shark;
import me.emmy.plugin.core.property.config.ConfigService;
import me.emmy.plugin.feature.spawn.SpawnService;
import me.emmy.plugin.feature.spawn.enums.LocationType;
import me.emmy.plugin.util.CC;
import me.emmy.plugin.util.Constants;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author Emmy
 * @project shark-practice
 * @since 10/08/2025
 */
@SuppressWarnings("unused")
@CommandAlias("shark|practice|spractice")
public class SharkCommand extends BaseCommand {
    @Default
    public void onExecute(CommandSender sender) {
        Arrays.asList(
                "",
                "&9&l\uD83E\uDD88 " + Constants.PLUGIN_NAME.toUpperCase() + " PRACTICE \uD83E\uDD88",
                "",
                " &f▢ &9Author: &f" + Constants.AUTHOR,
                " &f▢ &9Version: &f" + Constants.VERSION,
                "",
                " &f▢ &9GitHub: &f" + Constants.GITHUB,
                " &f▢ &9Discord: &f" + Constants.DISCORD,
                ""
        ).forEach(line -> sender.sendMessage(CC.translateLegacy(line)));
    }

    @Subcommand("reload")
    @CommandPermission("shark.command.kit")
    public void onReload(CommandSender sender) {
        sender.sendMessage(CC.translateLegacy("&fReloading &b&l" + Constants.PLUGIN_NAME + " PRACTICE &f..."));
        Shark.getInstance().getService(ConfigService.class).reloadConfigs();
        sender.sendMessage(CC.translateLegacy("&aSuccessfully reloaded &b&l" + Constants.PLUGIN_NAME + " PRACTICE &a!"));
    }

    @Subcommand("setspawn")
    @CommandPermission("shark.command.setspawn")
    public void onSetSpawn(Player player, LocationType type) {
        LocationType locationType;
        try {
            locationType = LocationType.valueOf(type.name().toUpperCase());
        } catch (IllegalArgumentException exception) {
            player.sendMessage(CC.translateLegacy("&cInvalid spawn type specified! Valid types are: " + Arrays.toString(LocationType.values())));
            return;
        }

        Shark.getInstance().getService(SpawnService.class).updateLocation(locationType, player.getLocation());
        player.sendMessage(CC.translateLegacy("&aSpawn location for &b" + locationType.name().toLowerCase().replace("_", " ") + " &aset successfully updated!"));
    }
}