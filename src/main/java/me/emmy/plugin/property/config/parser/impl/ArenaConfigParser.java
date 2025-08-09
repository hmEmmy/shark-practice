package me.emmy.plugin.property.config.parser.impl;

import me.emmy.plugin.Dream;
import me.emmy.plugin.feature.arena.model.Arena;
import me.emmy.plugin.property.config.ConfigService;
import me.emmy.plugin.property.config.parser.ConfigParser;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
public class ArenaConfigParser implements ConfigParser<Arena> {
    private final FileConfiguration arenasConfig = null;

    @Override
    public void configToModel(String path, Arena model) {

    }

    @Override
    public void modelToConfig(String path, Arena model) {

    }
}
