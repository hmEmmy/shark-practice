package me.emmy.plugin.feature.kit;

import lombok.Getter;
import me.emmy.plugin.core.parser.impl.KitParser;
import me.emmy.plugin.core.property.config.ConfigService;
import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.core.service.annotation.ServiceRegistryPriority;
import me.emmy.plugin.util.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project shark-practice
 * @since 09/08/2025
 */
@Getter
@ServiceRegistryPriority(value = 100)
public class KitService implements ServiceRegistryMethodProvider {
    private final List<Kit> kits = new ArrayList<>();
    private KitParser kitParser;

    @Override
    public void initialize() {
        this.kitParser = new KitParser();
        ConfigService configService = this.getPlugin().getService(ConfigService.class);
        FileConfiguration kitConfig = configService.getKitsConfig();
        if (kitConfig == null) {
            Logger.error("Kits configuration file not found or not loaded properly.");
            return;
        }

        ConfigurationSection kitsSection = kitConfig.getConfigurationSection("kits");
        if (kitsSection == null) {
            Logger.warn("No kits found in the configuration file.");
            return;
        }

        for (String kitName : kitsSection.getKeys(false)) {
            String path = "kits." + kitName + ".";
            Kit kit = new Kit(kitName);
            this.kitParser.configToModel(path, kit);
            this.kits.add(kit);
        }
    }

    /**
     * Removes a kit from the configuration file and the internal list.
     *
     * @param kit the Kit object to remove.
     */
    public void removeKit(Kit kit) {
        FileConfiguration config = this.getPlugin().getService(ConfigService.class).getKitsConfig();
        if (config == null) {
            Logger.error("Kits configuration file not found or not loaded properly.");
            return;
        }

        config.set("kits." + kit.getName(), null);
        this.kits.remove(kit);
        this.getPlugin().getService(ConfigService.class).saveFileConfiguration(config);
        Logger.info("Kit " + kit.getName() + " has been removed successfully.");
    }

    /**
     * Creates a new kit and saves it to the configuration file.
     *
     * @param kit the Kit object to create.
     */
    public void createKit(Kit kit) {
        this.kits.add(kit);
        this.saveKit(kit);
        Logger.info("Kit " + kit.getName() + " has been created successfully.");
    }

    /**
     * Saves a kit to the configuration file.
     *
     * @param kit the Kit object to save.
     */
    public void saveKit(Kit kit) {
        String path = "kits." + kit.getName() + ".";
        this.kitParser.modelToConfig(path, kit);
    }

    /**
     * Retrieves a kit by its name.
     *
     * @param name the name of the kit to retrieve.
     * @return the Kit object if found, or null if not found.
     */
    public Kit getKit(String name) {
        return this.kits.stream()
                .filter(kit -> kit.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void shutdown() {
        this.kits.forEach(this::saveKit);
    }
}