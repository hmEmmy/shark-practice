package me.emmy.plugin.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@UtilityClass
public class CC {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    /**
     * Parses input string which can contain legacy (&) color codes and MiniMessage tags.
     *
     * @param message input string with & codes and/or MiniMessage tags like <red>
     * @return Adventure Component with colors applied
     */
    public @NotNull Component translate(String message) {
        Component legacyComponent = legacySerializer.deserialize(message);
        String miniMessageString = miniMessage.serialize(legacyComponent);
        return miniMessage.deserialize(miniMessageString);
    }
}