package me.emmy.plugin.util;

import lombok.experimental.UtilityClass;
import me.emmy.plugin.Dream;

import javax.management.DescriptorRead;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
@UtilityClass
public class Constants {
    public String PLUGIN_NAME = Dream.getInstance().getName();
    public String PREFIX = "§7[§b" + PLUGIN_NAME + "§7] §r";
}