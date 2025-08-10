package me.emmy.plugin.command.impl.main;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.emmy.plugin.Dream;
import me.emmy.plugin.core.property.config.ConfigService;
import me.emmy.plugin.util.CC;
import me.emmy.plugin.util.Constants;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Dream
 * @since 10/08/2025
 */
@CommandAlias("dream|practice|dpractice")
public class DreamCommand extends BaseCommand {
    @Default
    public void onExecute(CommandSender sender) {
        Arrays.asList(
                "",
                "&5&l" + Constants.PLUGIN_NAME.toUpperCase() + " PRACTICE",
                " &f▢ &5Author: &f" + Constants.AUTHOR,
                " &f▢ &5Version: &f" + Constants.VERSION,
                " &f▢ &5GitHub: &f" + Constants.GITHUB,
                ""
        ).forEach(line -> sender.sendMessage(CC.translateLegacy(line)));
    }

    @Subcommand("reload")
    public void onReload(CommandSender sender) {
        Dream.getInstance().getService(ConfigService.class).reloadConfigs();
        sender.sendMessage(CC.translateLegacy("&a" + Constants.PLUGIN_NAME + " has been reloaded successfully."));
    }
}