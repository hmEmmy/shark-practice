package me.emmy.plugin;

import lombok.Getter;
import me.emmy.plugin.registry.ServiceRegistry;
import me.emmy.plugin.registry.annotation.ServiceRegistryMethodProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public class Dream extends JavaPlugin {

    @Getter
    private static Dream instance;
    private ServiceRegistry serviceRegistry;

    @Override
    public void onEnable() {
        instance = this;

        this.checkPluginAuthor();

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
        return (T) this.serviceRegistry.getServices().stream().filter(service -> service.getClass().equals(clazz))
                .findFirst()
                .orElse(null);
    }

    private void checkPluginAuthor() {
        List<String> authors = this.getPluginMeta().getAuthors();
        String author = "Emmy";
        if (!authors.contains(author)) {
            System.exit(1);
        }
    }
}