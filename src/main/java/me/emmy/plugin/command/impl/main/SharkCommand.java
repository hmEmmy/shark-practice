package me.emmy.plugin.command.impl.main;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.emmy.plugin.Shark;
import me.emmy.plugin.core.property.config.ConfigService;
import me.emmy.plugin.util.CC;
import me.emmy.plugin.util.Constants;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Shark
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
        Shark.getInstance().getService(ConfigService.class).reloadConfigs();
        sender.sendMessage(CC.translateLegacy("&a" + Constants.PLUGIN_NAME + " has been reloaded successfully."));
    }
}