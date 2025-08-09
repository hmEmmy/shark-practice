package me.emmy.plugin.registry;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.Getter;
import me.emmy.plugin.Dream;
import me.emmy.plugin.registry.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.registry.annotation.ServiceRegistryPriority;
import me.emmy.plugin.util.Logger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@Getter
public class ServiceRegistry {
    private final List<ServiceRegistryMethodProvider> services = new ArrayList<>();
    private final String PACKAGE_DIRECTORY = "me.emmy.plugin";

    public ServiceRegistry() {
        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages(PACKAGE_DIRECTORY)
                .enableClassInfo()
                .enableAnnotationInfo()
                .scan()) {

            for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(ServiceRegistryPriority.class.getName())) {
                Class<?> clazz = classInfo.loadClass();
                if (ServiceRegistryMethodProvider.class.isAssignableFrom(clazz)) {
                    ServiceRegistryMethodProvider service;

                    try {
                        Constructor<?> constructor = clazz.getDeclaredConstructor(Dream.class);
                        service = (ServiceRegistryMethodProvider) constructor.newInstance(Dream.getInstance());
                    } catch (NoSuchMethodException exception) {
                        service = (ServiceRegistryMethodProvider) clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception exception) {
                        Logger.exception("Failed to instantiate service: " + clazz.getSimpleName(), exception);
                        continue;
                    }

                    this.services.add(service);
                    Logger.info(clazz.getSimpleName() + " &a✔");
                }
            }

            this.services.sort(Comparator.comparingInt(service ->
                    service.getClass().getAnnotation(ServiceRegistryPriority.class).value()
            ));

        } catch (Exception exception) {
            Logger.exception("Failed to initialize service registry", exception);
        }

        this.services.forEach(ServiceRegistryMethodProvider::initialize);
    }

    /**
     * Responsible for shutting down all services in the registry.
     * This method iterates through each service and calls its shutdown method.
     */
    public void shutdown() {
        this.services.sort(Comparator.comparingInt(service -> service.getClass().getAnnotation(ServiceRegistryPriority.class).value()).reversed());
    }
}