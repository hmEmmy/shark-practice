package me.emmy.plugin.user;

import me.emmy.plugin.core.service.annotation.ServiceRegistryMethodProvider;
import me.emmy.plugin.core.service.annotation.ServiceRegistryPriority;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Emmy
 * @project Dream
 * @since 10/08/2025
 */
@ServiceRegistryPriority(500)
public class UserService implements ServiceRegistryMethodProvider {
    private final Map<UUID, User> users = new HashMap<>();

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

    @Override
    public void shutdown() {
        this.users.values().forEach(User::save);
    }
}