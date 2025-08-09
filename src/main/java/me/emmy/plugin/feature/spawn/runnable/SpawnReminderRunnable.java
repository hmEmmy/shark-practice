package me.emmy.plugin.feature.spawn.runnable;

import me.emmy.plugin.Dream;
import me.emmy.plugin.feature.spawn.enums.LocationType;
import me.emmy.plugin.util.CC;
import me.emmy.plugin.util.Constants;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Dream
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
        Arrays.asList(
                "",
                "&c" + Constants.PREFIX,
                this.locationType.name() + " spawn is NOT set!",
                "&7Set it using &e/spawn " + this.locationType.name().toLowerCase() + "&7.",
                ""
        ).forEach(line -> Dream.getInstance().getServer().getConsoleSender().sendMessage(CC.translate(line)));
    }
}