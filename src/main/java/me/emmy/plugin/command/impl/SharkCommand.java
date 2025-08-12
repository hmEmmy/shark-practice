package me.emmy.plugin.command.impl;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.emmy.plugin.Shark;
import me.emmy.plugin.config.ConfigService;
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
                "<blue><bold>\uD83E\uDD88 " + Constants.PLUGIN_NAME.toUpperCase() + " PRACTICE \uD83E\uDD88",
                "",
                " <white>▢ <blue>Author: <white>" + Constants.AUTHOR,
                " <white>▢ <blue>Version: <white>" + Constants.VERSION,
                "",
                " <white>▢ <blue>GitHub: <white>" + Constants.GITHUB,
                " <white>▢ <blue>Discord: <white>" + Constants.DISCORD,
                ""
        ).forEach(line -> sender.sendMessage(CC.translate(line)));
    }

    @Subcommand("reload")
    @CommandPermission("shark.command.kit")
    public void onReload(CommandSender sender) {
        sender.sendMessage(CC.translate("<white>Reloading <blue><bold>" + Constants.PLUGIN_NAME + " PRACTICE <white>..."));
        Shark.getInstance().getService(ConfigService.class).reloadConfigs();
        sender.sendMessage(CC.translate("<green>Successfully reloaded <blue><bold>" + Constants.PLUGIN_NAME + " PRACTICE <green>!"));
    }

    @Subcommand("setspawn")
    @CommandPermission("shark.command.setspawn")
    public void onSetSpawn(Player player, LocationType type) {
        LocationType locationType;
        try {
            locationType = LocationType.valueOf(type.name().toUpperCase());
        } catch (IllegalArgumentException exception) {
            player.sendMessage(CC.translate("<red>Invalid spawn type specified! Valid types are: " + Arrays.toString(LocationType.values())));
            return;
        }

        Shark.getInstance().getService(SpawnService.class).updateLocation(locationType, player.getLocation());
        player.sendMessage(CC.translate("<green>Spawn location for <blue>" + locationType.name().toLowerCase().replace("_", " ") + " <green>set successfully updated!"));
    }
}