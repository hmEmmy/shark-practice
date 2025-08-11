package me.emmy.plugin.config;

import lombok.Getter;
import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.core.service.annotation.ServiceRegistryPriority;
import me.emmy.plugin.util.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project shark-practice
 * @since 09/08/2025
 */
@Getter
@ServiceRegistryPriority(value = 10)
public class ConfigService implements ServiceRegistryMethodProvider {
    private final Map<String, FileConfiguration> fileConfigurations = new HashMap<>();

    private final String[] configFiles = {
            "locale.yml",
            "settings.yml",

            "cache/kits.yml",
            "settings/hotbar.yml",
    };

    private FileConfiguration localeConfig;
    private FileConfiguration settingsConfig;
    private FileConfiguration kitsConfig;
    private FileConfiguration hotbarConfig;

    @Override
    public void initialize() {
        for (String fileName : this.configFiles) {
            File file = this.getFile(fileName);
            if (!file.exists()) {
                this.getPlugin().saveResource(fileName, false);
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            this.fileConfigurations.put(fileName, config);
        }
        this.assignConfigFields();
    }

    /**
     * Reloads all configuration files from disk.
     */
    public void reloadConfigs() {
        this.assignConfigFields();
    }

    /**
     * Method to get a FileConfiguration by its file name.
     *
     * @param fileName the name of the configuration file.
     * @return the FileConfiguration if found, otherwise null.
     */
    public FileConfiguration getFileConfiguration(String fileName) throws IllegalArgumentException {
        return this.fileConfigurations.get(fileName);
    }

    /**
     * Method to get a File object for a given file name.
     *
     * @param fileName the name of the file.
     * @return the File object corresponding to the file name.
     */
    public File getFile(String fileName) throws IllegalArgumentException {
        return new File(this.getPlugin().getDataFolder(), fileName);
    }

    /**
     * Method to save a FileConfiguration to its corresponding file.
     *
     * @param fileConfiguration the FileConfiguration to save.
     */
    public void saveFileConfiguration(FileConfiguration fileConfiguration) {
        if (fileConfiguration == null) {
            Logger.error("FileConfiguration is null, cannot save.");
            return;
        }

        try {
            String fileName = null;
            for (Map.Entry<String, FileConfiguration> entry : fileConfigurations.entrySet()) {
                if (entry.getValue().equals(fileConfiguration)) {
                    fileName = entry.getKey();
                    break;
                }
            }

            if (fileName != null) {
                fileConfiguration.save(this.getFile(fileName));
            } else {
                Logger.error("Could not find file name for configuration to save.");
            }
        } catch (Exception e) {
            Logger.exception("Failed to save configuration file: " + fileConfiguration.getName(), e);
        }
    }

    private void assignConfigFields() {
        this.localeConfig = this.getFileConfiguration("locale.yml");
        this.settingsConfig = this.getFileConfiguration("settings.yml");
        this.kitsConfig = this.getFileConfiguration("cache/kits.yml");
        this.hotbarConfig = this.getFileConfiguration("settings/hotbar.yml");

        if (this.localeConfig == null || this.settingsConfig == null) {
            Logger.error("Failed to load configuration files.");
        }
    }
}