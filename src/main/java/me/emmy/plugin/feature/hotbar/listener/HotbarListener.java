package me.emmy.plugin.feature.hotbar.listener;

import me.emmy.plugin.Shark;
import me.emmy.plugin.feature.hotbar.HotbarService;
import me.emmy.plugin.feature.hotbar.record.HotbarItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * @author Emmy
 * @project shark-practice
 * @since 11/08/2025
 */
public class HotbarListener implements Listener {
    private final Shark plugin;

    /**
     * Constructor for the HotbarListener class.
     *
     * @param plugin the instance of the Shark plugin
     */
    public HotbarListener(Shark plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Action action = event.getAction();
        if ((action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        HotbarService hotbarService = this.plugin.getService(HotbarService.class);

        Player player = event.getPlayer();
        ItemStack clickedItem = player.getInventory().getItemInMainHand();

        if (clickedItem.getType().isAir()) {
            return;
        }

        if (!clickedItem.hasItemMeta()) {
            return;
        }

        HotbarItem hotbarItem = hotbarService.getHotbarItem(clickedItem);
        if (hotbarItem == null) {
            return;
        }

        player.performCommand(hotbarItem.command());
    }
}