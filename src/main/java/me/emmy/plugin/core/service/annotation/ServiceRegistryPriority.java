package me.emmy.plugin.core.service.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used to specify the priority of a service in the service registry.
 * Services with lower priority values will be initialized before those with higher values.
 * This is useful for controlling the order of service initialization and shutdown.
 *
 * @author Emmy
 * @project Shark
 * @since 09/08/2025
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceRegistryPriority {
    /**
     * The priority value for the service.
     * Higher values indicate higher priority.
     *
     * @return the priority value
     */
    int value();
}