package me.emmy.plugin.command;

import co.aikar.commands.BukkitCommandManager;
import me.emmy.plugin.command.impl.KitCommand;
import me.emmy.plugin.command.impl.main.DreamCommand;
import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.core.service.annotation.ServiceRegistryPriority;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Dream
 * @since 10/08/2025
 */
@ServiceRegistryPriority(value = 900)
public class CommandRegistry implements ServiceRegistryMethodProvider {
    private final BukkitCommandManager commandManagerAPI = new BukkitCommandManager(this.getPlugin());

    @Override
    public void initialize() {
        Arrays.asList(
                new DreamCommand(),
                new KitCommand()
        ).forEach(this.commandManagerAPI::registerCommand);
    }
}
