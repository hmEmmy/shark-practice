package me.emmy.plugin.command.impl.management;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.emmy.plugin.Shark;
import me.emmy.plugin.user.User;
import me.emmy.plugin.user.UserService;
import me.emmy.plugin.util.CC;
import org.bukkit.entity.Player;

import java.awt.*;

/**
 * @author Emmy
 * @project shark-practice
 * @since 12/08/2025
 */
@CommandAlias("buildmode")
@CommandPermission("shark.command.buildmode")
@SuppressWarnings("unused")
public class BuildModeCommand extends BaseCommand {
    @Default
    public void execute(Player player) {
        UserService userService = Shark.getInstance().getService(UserService.class);
        User user = userService.getUser(player.getUniqueId());

        user.setBuildMode(!user.isBuildMode());
        player.sendMessage(CC.translate("<white>Successfully switched build mode! <gray>(<blue>" + (user.isBuildMode() ? "Enabled" : "Disabled") + "<gray>)"));
    }
}