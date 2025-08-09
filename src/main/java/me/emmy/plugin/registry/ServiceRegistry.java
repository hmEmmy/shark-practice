package me.emmy.plugin.registry;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.Getter;
import me.emmy.plugin.Dream;
import me.emmy.plugin.registry.annotation.ServiceMetadata;
import me.emmy.plugin.registry.annotation.ServiceRegistryData;
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
    private final List<ServiceRegistryData> services = new ArrayList<>();
    private final String PACKAGE_DIRECTORY = "me.emmy.plugin";

    public ServiceRegistry() {
        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages(PACKAGE_DIRECTORY)
                .enableClassInfo()
                .enableAnnotationInfo()
                .scan()) {

            for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(ServiceMetadata.class.getName())) {
                Class<?> clazz = classInfo.loadClass();
                if (ServiceRegistryData.class.isAssignableFrom(clazz)) {
                    ServiceRegistryData service;

                    try {
                        Constructor<?> constructor = clazz.getDeclaredConstructor(Dream.class);
                        service = (ServiceRegistryData) constructor.newInstance(Dream.getInstance());
                    } catch (NoSuchMethodException exception) {
                        service = (ServiceRegistryData) clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception exception) {
                        Logger.exception("Failed to instantiate service: " + clazz.getSimpleName(), exception);
                        continue;
                    }

                    this.services.add(service);
                    Logger.info(clazz.getSimpleName() + " &a✔");
                }
            }

            this.services.sort(Comparator.comparingInt(service ->
                    service.getClass().getAnnotation(ServiceMetadata.class).priority()
            ));

        } catch (Exception exception) {
            Logger.exception("Failed to initialize service registry", exception);
        }

        this.services.forEach(ServiceRegistryData::initialize);
    }

    /**
     * Responsible for shutting down all services in the registry.
     * This method iterates through each service and calls its shutdown method.
     */
    public void shutdown() {
        this.services.forEach(ServiceRegistryData::shutdown);
    }
}