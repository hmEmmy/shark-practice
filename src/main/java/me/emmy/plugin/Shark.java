package me.emmy.plugin;

import lombok.Getter;
import me.emmy.plugin.core.service.ServiceRegistry;
import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Shark extends JavaPlugin {

    @Getter
    private static Shark instance;
    private ServiceRegistry serviceRegistry;

    @Override
    public void onEnable() {
        instance = this;

        this.serviceRegistry = new ServiceRegistry();
        this.serviceRegistry.initialize();
    }

    @Override
    public void onDisable() {
        this.serviceRegistry.shutdown();
    }

    /**
     * Retrieves a service from the registry by its class type.
     *
     * @param clazz The class type of the service to retrieve.
     * @param <T>   The type of the service.
     * @return The service instance if found, otherwise null.
     */
    @SuppressWarnings("unchecked")
    public <T extends ServiceRegistryMethodProvider> T getService(Class<T> clazz) {
        return (T) this.serviceRegistry.getServices().stream().filter(service -> service.getClass().equals(clazz)).findFirst().orElse(null);
    }
}