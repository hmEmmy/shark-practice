package me.emmy.plugin.command.impl.queue;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.emmy.plugin.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project shark-practice
 * @since 11/08/2025
 */
@CommandAlias("ranked")
public class RankedCommand extends BaseCommand {
    @Default
    public void onExecute(Player player) {
        player.sendMessage(CC.translateLegacy("&cNot yet implemented!"));
    }
}
