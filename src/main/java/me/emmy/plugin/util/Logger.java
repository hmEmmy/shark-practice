package me.emmy.plugin.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@UtilityClass
public class Logger {
    public final ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();

    /**
     * Sends an info message to the console.
     *
     * @param message the message to log.
     */
    public void info(String message) {
        consoleSender.sendMessage(CC.translate(Constants.PREFIX + "(INFO) <green>" + message));
    }

    /**
     * Sends a warning message to the console.
     *
     * @param message the message to log.
     */
    public void warn(String message) {
        consoleSender.sendMessage(CC.translate(Constants.PREFIX + "(WARN) <yellow>" + message));
    }

    /**
     * Sends an error message to the console.
     *
     * @param message the message to log.
     */
    public void error(String message) {
        consoleSender.sendMessage(CC.translate(Constants.PREFIX + "(ERROR) <red>" + message));
    }

    /**
     * Sends an exception message to the console.
     *
     * @param message   the message to log.
     * @param throwable the exception to log.
     */
    public void exception(String message, Throwable throwable) {
        consoleSender.sendMessage(CC.translate(Constants.PREFIX + "(EXCEPTION) <dark_red>" + message));
        throwable.printStackTrace();
    }
}