package me.emmy.plugin.feature.kit.enums;

import lombok.Getter;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@Getter
public enum KitSetting {
    BOXING("Boxing", "Description for Boxing kit"),
    SUMO("Sumo", "Description for Sumo kit"),

    ;

    private final String name;
    private final String description;

    /**
     * Constructor for the KitSetting enum.
     *
     * @param name        the name of the kit setting
     * @param description a brief description of the kit setting
     */
    KitSetting(String name, String description) {
        this.name = name;
        this.description = description;
    }
}