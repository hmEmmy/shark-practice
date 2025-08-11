package me.emmy.plugin.user.listener;

import me.emmy.plugin.Shark;
import me.emmy.plugin.feature.spawn.SpawnService;
import me.emmy.plugin.feature.spawn.enums.LocationType;
import me.emmy.plugin.user.User;
import me.emmy.plugin.user.UserService;
import me.emmy.plugin.util.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Emmy
 * @project shark-practice
 * @since 10/08/2025
 */
public class UserListener implements Listener {
    @EventHandler
    private void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        User user = Shark.getInstance().getService(UserService.class).getUser(player.getUniqueId());
        this.checkUser(user, player);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = Shark.getInstance().getService(UserService.class).getUser(player.getUniqueId());
        user.save();

        event.joinMessage(null);

        Shark.getInstance().getService(SpawnService.class).teleport(player, LocationType.LOBBY);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = Shark.getInstance().getService(UserService.class).getUser(player.getUniqueId());
        user.save();

        event.quitMessage(null);

        Shark.getInstance().getService(UserService.class).removeUser(user);
    }

    @EventHandler
    private void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        User user = Shark.getInstance().getService(UserService.class).getUser(player.getUniqueId());
        user.save();

        Shark.getInstance().getService(UserService.class).removeUser(user);
    }

    /**
     * Not necessarily needed, but checks if the user exists.
     * Just to prevent any issues with null users.
     *
     * @param user   The user to check.
     * @param player The player associated with the user.
     */
    private void checkUser(User user, Player player) {
        if (user == null) {
            Logger.warn("User not found for player " + player.getName() + ". Creating a new user.");
            user = new User(player.getUniqueId(), player.getName());
            Shark.getInstance().getService(UserService.class).addUser(user);
        }
    }
}