package me.emmy.plugin.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Emmy
 * @project shark-practice
 * @since 09/08/2025
 */
@UtilityClass
public class CC {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    /**
     * Translates a message using MiniMessage syntax.
     *
     * @param message the message to translate
     * @return the translated Component
     */
    public @NotNull Component translate(String message) {
        return miniMessage.deserialize(message);
    }

    /**
     * Translates a legacy message using the legacy serializer.
     *
     * @param message the message to translate
     * @return the translated Component
     */
    public @NotNull Component translateLegacy(String message) {
        return legacySerializer.deserialize(message);
    }
}