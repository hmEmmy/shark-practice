package me.emmy.plugin.registry.annotation;

import me.emmy.plugin.Dream;

/**
 * This interface represents a service registry data structure.
 * It provides methods to initialize and shut down the service.
 * <p>
 * Service classes require the {@link ServiceRegistryPriority} annotation.
 *
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
public interface ServiceRegistryMethodProvider {
    /**
     * Method to initialize the given service.
     */
    default void initialize() {
    }

    /**
     * Method to shut down the given service.
     */
    default void shutdown() {
    }

    /**
     * Returns the instance of the Dream plugin.
     *
     * @return the Dream plugin instance
     */
    default Dream getPlugin() {
        return Dream.getInstance();
    }
}