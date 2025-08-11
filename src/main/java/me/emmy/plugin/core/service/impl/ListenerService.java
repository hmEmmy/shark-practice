package me.emmy.plugin.core.service.impl;

import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.core.service.annotation.ServiceRegistryPriority;
import me.emmy.plugin.user.user.UserListener;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Shark
 * @since 10/08/2025
 */
@ServiceRegistryPriority(value = 800)
public class ListenerService implements ServiceRegistryMethodProvider {
    @Override
    public void initialize() {
        Arrays.asList(
                new UserListener()
        ).forEach(listener -> this.getPlugin().getServer().getPluginManager().registerEvents(listener, this.getPlugin()));
    }
}
