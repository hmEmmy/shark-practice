package me.emmy.plugin;

import lombok.Getter;

/**
 * This class represents the API of the {@link Dream} plugin.
 * It provides a singleton instance that can be accessed throughout the plugin.
 *
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@Getter
public class DreamAPI {

    @Getter
    private static DreamAPI instance;

    public DreamAPI() {
        instance = this;
    }
}