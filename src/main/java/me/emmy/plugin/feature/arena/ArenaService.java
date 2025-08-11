package me.emmy.plugin.feature.arena;

import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.core.service.annotation.ServiceRegistryPriority;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project Shark
 * @since 09/08/2025
 */
@ServiceRegistryPriority(value = 120)
public class ArenaService implements ServiceRegistryMethodProvider {
    private final List<Arena> arenas = new ArrayList<>();

    @Override
    public void initialize() {

    }
}