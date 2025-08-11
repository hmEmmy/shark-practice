package me.emmy.plugin;

import lombok.Getter;

/**
 * This class represents the API of the {@link Shark} plugin.
 * It provides a singleton instance that can be accessed throughout the plugin.
 *
 * @author Emmy
 * @project Shark
 * @since 09/08/2025
 */
@Getter
public class SharkAPI {

    @Getter
    private static SharkAPI instance;

    public SharkAPI() {
        instance = this;
    }
}