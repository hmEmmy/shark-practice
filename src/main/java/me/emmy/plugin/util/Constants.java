package me.emmy.plugin.util;

import lombok.experimental.UtilityClass;
import me.emmy.plugin.Shark;

/**
 * @author Emmy
 * @project Shark
 * @since 09/08/2025
 */
@UtilityClass
public class Constants {
    public String PLUGIN_NAME = Shark.getInstance().getName();
    public String PREFIX = "<gray>[<blue>" + PLUGIN_NAME + "<gray>]<reset> ";

    public String AUTHOR = "Emmy";
    public String GITHUB = "https://github.com/hmEmmy";
    public String DISCORD = "https://discord.gg/eT4B65k5E4";

    public String VERSION = Shark.getInstance().getPluginMeta().getVersion();
    public String SPIGOT = Shark.getInstance().getServer().getName();
}