package me.emmy.plugin.registry.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used to specify the priority of a service in the service registry.
 * Services with higher priority values will be initialized before those with lower values.
 * The priority value can be set to any integer, with higher values indicating higher priority.
 *
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceRegistryPriority {
    int priority();
}