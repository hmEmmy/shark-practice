package me.emmy.plugin.core.parser;

/**
 * @author Emmy
 * @project Dream
 * @since 09/08/2025
 */
public interface Parser<T> {
    /**
     * Method to parse a configuration file and populate a model of type {@code T}.
     *
     * @param path  the path in the configuration file where the model data is stored.
     * @param model the model of type T to be populated with data from the configuration.
     */
    void configToModel(String path, T model);

    /**
     * Method to retrieve values from a model of type {@code T} and save them to a configuration file.
     *
     * @param path  the path in the configuration file where the model data will be saved.
     * @param model the model of type T to be converted and saved.
     */
    void modelToConfig(String path, T model);
}