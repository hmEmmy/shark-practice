package me.emmy.plugin.property.config.parser.impl;

import me.emmy.plugin.Dream;
import me.emmy.plugin.feature.kit.enums.KitCategory;
import me.emmy.plugin.feature.kit.enums.KitSetting;
import me.emmy.plugin.feature.kit.model.Kit;
import me.emmy.plugin.property.config.ConfigService;
import me.emmy.plugin.property.config.parser.ConfigParser;
import me.emmy.plugin.util.Serializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
public class KitConfigParser implements ConfigParser<Kit> {
    private final FileConfiguration kitConfig = Dream.getInstance().getService(ConfigService.class).getKitsConfig();

    @Override
    public void configToModel(String path, Kit kit) {
        this.kitConfig.set(path + ".description", kit.getDescription());
        this.kitConfig.set(path + ".disclaimer", kit.getDisclaimer());

        this.kitConfig.set(path + ".material", kit.getMaterial());

        this.kitConfig.set(path + ".enabled", kit.isEnabled());

        this.kitConfig.set(path + ".items", Serializer.serializeItemStack(kit.getItems()));
        this.kitConfig.set(path + ".armor", Serializer.serializeItemStack(kit.getArmor()));

        this.kitConfig.set(path + ".category", kit.getCategory().name());

        List<String> settingNames = kit.getSettings().stream()
                .map(Enum::name)
                .toList();
        this.kitConfig.set(path + ".settings", settingNames);

        Dream.getInstance().getService(ConfigService.class).saveFileConfiguration(this.kitConfig);
    }

    @Override
    public void modelToConfig(String path, Kit kit) {
        kit.setDescription(this.kitConfig.getString(path + ".description"));
        kit.setDisclaimer(this.kitConfig.getString(path + ".disclaimer"));

        kit.setMaterial(this.kitConfig.getString(path + ".material"));

        kit.setEnabled(this.kitConfig.getBoolean(path + ".enabled"));

        kit.setItems(Serializer.deserializeItemStack(this.kitConfig.getString(path + ".items")));
        kit.setArmor(Serializer.deserializeItemStack(this.kitConfig.getString(path + ".armor")));

        EnumSet<KitSetting> settings = this.parseSettings(path);
        kit.setSettings(settings);

        kit.setCategory(KitCategory.valueOf(this.kitConfig.getString(path + ".category")));
    }

    /**
     * Parses the settings from the configuration file for a specific kit.
     * Reads the settings from the specified path in the kits.yml file located at "cache/kits.yml".
     *
     * @param path the path in the configuration file where the kit settings are stored.
     * @return an EnumSet of KitSetting containing the parsed settings.
     */
    private @NotNull EnumSet<KitSetting> parseSettings(String path) {
        List<String> existingSettings = this.kitConfig.getStringList(path + ".settings");
        EnumSet<KitSetting> settings = EnumSet.noneOf(KitSetting.class);
        for (String name : existingSettings) {
            try {
                settings.add(KitSetting.valueOf(name));
            } catch (IllegalArgumentException ignored) {
            }
        }
        return settings;
    }
}