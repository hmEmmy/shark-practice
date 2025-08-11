package me.emmy.plugin.core.service.annotation;

import me.emmy.plugin.Shark;

/**
 * This interface represents a service registry data structure.
 * It provides methods to initialize and shut down the service.
 * <p>
 * Service classes require the {@link ServiceRegistryPriority} annotation.
 *
 * @author Emmy
 * @project shark-practice
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
     * Returns the instance of the Shark plugin.
     *
     * @return the Shark plugin instance
     */
    default Shark getPlugin() {
        return Shark.getInstance();
    }
}