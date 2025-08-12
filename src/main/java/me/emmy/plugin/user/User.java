package me.emmy.plugin.user;

import lombok.Getter;
import lombok.Setter;
import me.emmy.plugin.user.enums.UserState;

import java.util.UUID;

/**
 * @author Emmy
 * @project shark-practice
 * @since 10/08/2025
 */
@Getter
@Setter
public class User {
    private final UUID uuid;
    private String name;
    private UserState state;

    private boolean buildMode = false;

    /**
     * Constructor for the User class.
     *
     * @param uuid the unique identifier for the user.
     * @param name the name of the user.
     */
    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.state = UserState.LOBBY;
    }

    public void save() {
        // Logic to save the user data, e.g., to a database or file.
        // This is a placeholder for the actual implementation.
    }

    public void load() {
        // Logic to load the user data, e.g., from a database or file.
        // This is a placeholder for the actual implementation.
    }
}