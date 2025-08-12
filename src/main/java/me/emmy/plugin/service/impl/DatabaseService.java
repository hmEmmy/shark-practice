package me.emmy.plugin.service.impl;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.emmy.plugin.config.ConfigService;
import me.emmy.plugin.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.service.annotation.ServiceRegistryPriority;
import me.emmy.plugin.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project shark-practice
 * @since 12/08/2025
 */
@Getter
@ServiceRegistryPriority(value = 20)
public class DatabaseService implements ServiceRegistryMethodProvider {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    @Override
    public void initialize() {
        FileConfiguration settingsConfig = this.getPlugin().getService(ConfigService.class).getSettingsConfig();
        String databaseName = settingsConfig.getString("MONGO-DATABASE.NAME");
        String connectionString = settingsConfig.getString("MONGO-DATABASE.CONNECTION-STRING");

        if (connectionString == null || connectionString.isEmpty()) {
            connectionString = "mongodb://localhost:27017";
            Logger.error("MongoDB connection string is missing. Defaulting to 'mongodb://localhost:27017'.");
        }

        if (databaseName == null || databaseName.isEmpty()) {
            databaseName = "shark_practice";
            Logger.error("MongoDB database name is missing. Defaulting to 'shark_practice'.");
        }

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .retryWrites(true)
                    .build();

            this.mongoClient = MongoClients.create(settings);
            this.mongoDatabase = mongoClient.getDatabase(databaseName);

            this.mongoDatabase.listCollectionNames().first();

        } catch (Exception exception) {
            Logger.exception("Failed to connect to MongoDB database: " + databaseName, exception);
            Bukkit.getPluginManager().disablePlugin(this.getPlugin());
        }
    }

    @Override
    public void shutdown() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
        }
    }
}