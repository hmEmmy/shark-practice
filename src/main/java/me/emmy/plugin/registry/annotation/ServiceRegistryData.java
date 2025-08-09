package me.emmy.plugin.registry.annotation;

/**
 * This interface represents a service registry data structure.
 * It provides methods to initialize and shut down the service.
 * <p>
 * Service classes require the {@link ServiceMetadata} annotation.
 *
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
public interface ServiceRegistryData {
    /**
     * Method to initialize the given service.
     */
    default void initialize() {}

    /**
     * Method to shut down the given service.
     */
    default void shutdown() {}
}