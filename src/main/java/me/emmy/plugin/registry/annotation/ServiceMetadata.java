package me.emmy.plugin.registry.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used to provide metadata for services in the plugin registry.
 * It specifies the priority of the service, which can be used to determine the order of service
 * initialization and shutdown.
 *
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceMetadata {
    int priority();
}