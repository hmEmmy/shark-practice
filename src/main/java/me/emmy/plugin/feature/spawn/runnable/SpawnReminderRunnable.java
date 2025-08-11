package me.emmy.plugin.feature.spawn.runnable;

import me.emmy.plugin.Shark;
import me.emmy.plugin.feature.spawn.SpawnService;
import me.emmy.plugin.feature.spawn.enums.LocationType;
import me.emmy.plugin.util.CC;
import me.emmy.plugin.util.Constants;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

/**
 * @author Emmy
 * @project shark-practice
 * @since 09/08/2025
 */
public class SpawnReminderRunnable extends BukkitRunnable {
    private final LocationType locationType;

    /**
     * Constructor for the SpawnReminderRunnable class.
     *
     * @param locationType the type of location for which the spawn reminder is set.
     */
    public SpawnReminderRunnable(LocationType locationType) {
        this.locationType = locationType;
    }

    @Override
    public void run() {
        if (Shark.getInstance().getService(SpawnService.class).getLocations().get(this.locationType) != null) {
            this.cancel();
            return;
        }

        Arrays.asList(
                "",
                Constants.PREFIX,
                this.locationType.name() + " spawn is NOT set!",
                "<white>Set it using <blue>/shark setspawn " + this.locationType.name().toUpperCase() + "<white>.",
                ""
        ).forEach(line -> Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(CC.translate(line))));
    }
}