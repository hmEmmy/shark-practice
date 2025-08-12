package me.emmy.plugin.user;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import me.emmy.plugin.Shark;
import me.emmy.plugin.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.service.annotation.ServiceRegistryPriority;
import me.emmy.plugin.service.impl.DatabaseService;
import me.emmy.plugin.user.enums.UserState;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Emmy
 * @project shark-practice
 * @since 10/08/2025
 */
@ServiceRegistryPriority(500)
public class UserService implements ServiceRegistryMethodProvider {
    private final Map<UUID, User> users = new HashMap<>();
    private MongoCollection<Document> userCollection;

    @Override
    public void initialize() {
        this.userCollection = Shark.getInstance().getService(DatabaseService.class).getMongoDatabase().getCollection("users");
    }

    /**
     * Retrieves a User by their UUID.
     * In case if it does not exist, a new User is created
     * with the UUID and the name of the offline player object obtained from Bukkit.
     *
     * @param uuid the UUID of the User to be retrieved.
     * @return the User object associated with the UUID.
     */
    public User getUser(UUID uuid) {
        if (!this.users.containsKey(uuid)) {
            User user = new User(uuid, Bukkit.getOfflinePlayer(uuid).getName());
            this.addUser(user);
        }

        return this.users.get(uuid);
    }

    /**
     * Initializes a new User and stores it in the map.
     *
     * @param user the User to be added.
     */
    public void addUser(User user) {
        this.users.put(user.getUuid(), user);
    }

    /**
     * Removes a User from the map of profiles.
     *
     * @param user the User to be removed.
     */
    public void removeUser(User user) {
        this.users.remove(user.getUuid());
    }

    /**
     * Saves the User's data to the database.
     * This method creates a Document representation of the User
     * and replaces the existing document in the collection or inserts a new one if it does not exist.
     *
     * @param user the User to be saved.
     */
    public void saveUser(User user) {
        Document userDocument = new Document("uuid", user.getUuid().toString())
                .append("name", user.getName())
                ;

        this.userCollection.replaceOne(new Document("uuid", user.getUuid().toString()), userDocument, new ReplaceOptions().upsert(true));
    }

    /**
     * Loads the User's data from the database.
     * If the User does not exist in the database, it saves the User instead.
     *
     * @param user the User to be loaded.
     */
    public void loadUser(User user) {
        Document userDocument = this.userCollection.find(new Document("uuid", user.getUuid().toString())).first();
        if (userDocument == null) {
            this.saveUser(user);
            return;
        }

        user.setName(userDocument.getString("name"));
    }

    @Override
    public void shutdown() {
        this.users.values().forEach(User::save);
    }
}