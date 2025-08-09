package me.emmy.plugin.property.validator;

import lombok.Getter;
import me.emmy.plugin.Dream;
import me.emmy.plugin.registry.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.registry.annotation.ServiceRegistryPriority;

import java.util.List;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@Getter
@ServiceRegistryPriority(value = 1000)
public class PropertyValidator implements ServiceRegistryMethodProvider {

    @Override
    public void initialize() {
        List<String> provided = this.getPlugin().getPluginMeta().getAuthors();
        String lookingFor = "E" + "m" + "m" + "y";
        if (!provided.contains(lookingFor)) {
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                this.getPlugin().getServiceRegistry().getServices().clear();
            }
        }
    }
}