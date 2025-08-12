package me.emmy.plugin.feature.spawn.listener;

import me.emmy.plugin.Shark;
import me.emmy.plugin.user.User;
import me.emmy.plugin.user.UserService;
import me.emmy.plugin.user.enums.UserState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

/**
 * @author Emmy
 * @project shark-practice
 * @since 12/08/2025
 */
public class SpawnListener implements Listener {
    private final Shark plugin;

    /**
     * Constructor for the SpawnListener class.
     *
     * @param plugin the instance of the Shark plugin
     */
    public SpawnListener(Shark plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;
        if (user.isBuildMode()) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void onItemPickUp(PlayerAttemptPickupItemEvent event) {
        Player player = event.getPlayer();
        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;
        if (user.isBuildMode()) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;
        if (user.isBuildMode()) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;
        if (user.isBuildMode()) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void onHunger(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerHit(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;

        if (user.isBuildMode()) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;

        if (event.getInventory().getType() != InventoryType.PLAYER) {
            if (!user.isBuildMode()) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (!user.isBuildMode()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        User user = this.plugin.getService(UserService.class).getUser(player.getUniqueId());
        if (user.getState() != UserState.LOBBY) return;

        if (user.isBuildMode()) return;

        event.setCancelled(true);
    }
}