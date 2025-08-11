package me.emmy.plugin.feature.spawn;

import lombok.Getter;
import me.emmy.plugin.feature.spawn.enums.LocationType;
import me.emmy.plugin.core.property.config.ConfigService;
import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.core.service.annotation.ServiceRegistryPriority;
import me.emmy.plugin.util.Logger;
import me.emmy.plugin.util.Serializer;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.EnumMap;
import java.util.Map;


/**
 * @author Emmy
 * @project Shark
 * @since 09/08/2025
 */
@Getter
@ServiceRegistryPriority(value = 110)
public class SpawnService implements ServiceRegistryMethodProvider {
    private final Map<LocationType, Location> locations = new EnumMap<>(LocationType.class);

    @Override
    public void initialize() {
        FileConfiguration config = this.getPlugin().getService(ConfigService.class).getSettingsConfig();
        if (config == null) {
            Logger.error("Settings configuration file not found or not loaded properly.");
            return;
        }

        for (LocationType type : LocationType.values()) {
            String path = "locations." + type.name().toLowerCase().replace("_", "-");
            try {
                Location location = Serializer.deserializeLocation(config.getString(path));
                if (location != null) {
                    this.locations.put(type, location);
                } else {
                    //new SpawnReminderRunnable(type).runTaskTimer(this.getPlugin(), 0L, 20L);
                }
            } catch (Exception exception) {
                Logger.exception("Failed to deserialize location for " + type, exception);
            }
        }
    }

    /**
     * Updates the location for a specific LocationType and saves it to the configuration.
     *
     * @param locationType the type of location to update
     * @param location     the new location to set
     */
    public void updateLocation(LocationType locationType, Location location) {
        FileConfiguration config = this.getPlugin().getService(ConfigService.class).getSettingsConfig();
        if (config == null) {
            Logger.error("Settings configuration file not found or not loaded properly.");
            return;
        }

        String path = "locations." + locationType.name().toLowerCase().replace("_", "-");
        config.set(path, Serializer.serializeLocation(location));
        this.getPlugin().getService(ConfigService.class).saveFileConfiguration(config);
        this.locations.put(locationType, location);
        Logger.info("Updated location for " + locationType + " to " + location);
    }

    /**
     * Teleports a player to the specified location type.
     *
     * @param player       the player to teleport
     * @param locationType the type of location to teleport to
     */
    public void teleport(Player player, LocationType locationType) {
        Location location = this.locations.get(locationType);
        if (location == null) {
            Logger.error("Location for " + locationType + " is not set.");
            return;
        }

        player.teleport(location);
    }
}