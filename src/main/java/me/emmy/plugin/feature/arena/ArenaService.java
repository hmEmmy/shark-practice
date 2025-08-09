package me.emmy.plugin.feature.arena;

import me.emmy.plugin.feature.arena.model.Arena;
import me.emmy.plugin.registry.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.registry.annotation.ServiceRegistryPriority;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@ServiceRegistryPriority(value = 120)
public class ArenaService implements ServiceRegistryMethodProvider {
    private final List<Arena> arenas = new ArrayList<>();

    @Override
    public void initialize() {

    }
}