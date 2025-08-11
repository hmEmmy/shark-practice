package me.emmy.plugin.core.parser.impl;

import me.emmy.plugin.feature.arena.Arena;
import me.emmy.plugin.core.parser.Parser;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project Shark
 * @since 09/08/2025
 */
public class ArenaParser implements Parser<Arena> {
    private final FileConfiguration arenasConfig = null;

    @Override
    public void configToModel(String path, Arena model) {

    }

    @Override
    public void modelToConfig(String path, Arena model) {

    }
}
