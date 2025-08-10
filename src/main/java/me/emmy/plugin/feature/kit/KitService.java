package me.emmy.plugin.feature.kit;

import lombok.Getter;
import me.emmy.plugin.feature.kit.model.Kit;
import me.emmy.plugin.property.config.parser.impl.KitConfigParser;
import me.emmy.plugin.property.config.ConfigService;
import me.emmy.plugin.registry.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.registry.annotation.ServiceRegistryPriority;
import me.emmy.plugin.util.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@Getter
@ServiceRegistryPriority(value = 100)
public class KitService implements ServiceRegistryMethodProvider {
    private final List<Kit> kits = new ArrayList<>();
    private KitConfigParser kitParser;

    @Override
    public void initialize() {
        this.kitParser = new KitConfigParser();
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