package me.emmy.plugin.service;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.Getter;
import me.emmy.plugin.Shark;
import me.emmy.plugin.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.service.annotation.ServiceRegistryPriority;
import me.emmy.plugin.util.Constants;
import me.emmy.plugin.util.Logger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Emmy
 * @project shark-practice
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
                        Constructor<?> constructor = clazz.getDeclaredConstructor(Shark.class);
                        service = (ServiceRegistryMethodProvider) constructor.newInstance(Shark.getInstance());
                    } catch (NoSuchMethodException exception) {
                        service = (ServiceRegistryMethodProvider) clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception exception) {
                        Logger.exception("Failed to instantiate service: " + clazz.getSimpleName(), exception);
                        continue;
                    }
                    this.services.add(service);
                }
            }

            this.services.sort(Comparator.comparingInt(service ->
                    service.getClass().getAnnotation(ServiceRegistryPriority.class).value()
            ));
        } catch (Exception exception) {
            Logger.exception("Failed to initialize service registry", exception);
            throw new RuntimeException("Failed to load services.", exception);
        }
    }

    public void initialize() {
        Logger.print("");
        Logger.print("Initializing <blue><bold>" + Constants.PLUGIN_NAME.toUpperCase() + " PRACTICE <reset>services...");
        Logger.print("<gray><strikethrough>------------------------------------");

        this.services.forEach(service -> {
            service.initialize();
            Logger.info("<blue>" + service.getClass().getSimpleName() + " <green>✔");
        });

        Logger.print("<gray><strikethrough>------------------------------------");
        Logger.print("");

        Arrays.asList(
                "<blue><bold>\uD83E\uDD88 " + Constants.PLUGIN_NAME.toUpperCase() + " PRACTICE \uD83E\uDD88",
                "",
                " <white>▢ Author: <blue>" + Constants.AUTHOR,
                " <white>▢ Version: <blue>" + Constants.VERSION,
                "",
                " <white>▢ Discord: <blue>" + Constants.DISCORD,
                " <white>▢ GitHub: <blue>" + Constants.GITHUB,
                "",
                " <white>▢ Spigot: <blue>" + Constants.SPIGOT,
                ""
        ).forEach(Logger::print);
    }

    /**
     * Responsible for shutting down all services in the registry.
     * This method iterates through each service and calls its shutdown method.
     */
    public void shutdown() {
        this.services.stream()
                .sorted(Comparator.comparingInt(service -> service.getClass().getAnnotation(ServiceRegistryPriority.class).value()).reversed())
                .forEach(service -> {
                    try {
                        service.shutdown();
                        Logger.info(service.getClass().getSimpleName() + " shutdown complete");
                    } catch (Exception exception) {
                        Logger.exception("Failed to shutdown service: " + service.getClass().getSimpleName(), exception);
                    }
                })
        ;

        Logger.print("<red>" + Constants.PLUGIN_NAME.toUpperCase() + " PRACTICE <white>has been disabled.");
    }
}