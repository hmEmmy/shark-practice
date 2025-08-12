package me.emmy.plugin.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

/**
 * @author Emmy
 * @project shark-practice
 * @since 09/08/2025
 */
@UtilityClass
public class CC {
    public String MENU_BAR_LEGACY = "-------------------";
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    /**
     * Translates a message using MiniMessage syntax.
     *
     * @param message the message to translate
     * @return the translated Component
     */
    public @NotNull Component translate(String message) {
        return miniMessage.deserialize(message);
    }
}