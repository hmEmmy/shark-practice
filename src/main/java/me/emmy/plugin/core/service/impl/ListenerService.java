package me.emmy.plugin.core.service.impl;

import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.core.service.annotation.ServiceRegistryPriority;
import me.emmy.plugin.feature.hotbar.listener.HotbarListener;
import me.emmy.plugin.feature.spawn.listener.SpawnListener;
import me.emmy.plugin.library.menu.MenuListener;
import me.emmy.plugin.user.listener.UserListener;

import java.util.Arrays;

/**
 * @author Emmy
 * @project shark-practice
 * @since 10/08/2025
 */
@ServiceRegistryPriority(value = 800)
public class ListenerService implements ServiceRegistryMethodProvider {
    @Override
    public void initialize() {
        Arrays.asList(
                new UserListener(),
                new HotbarListener(),
                new SpawnListener(),
                new MenuListener()
        ).forEach(listener -> this.getPlugin().getServer().getPluginManager().registerEvents(listener, this.getPlugin()));
    }
}
