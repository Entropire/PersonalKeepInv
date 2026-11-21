package dev.entropire.personalkeepinv;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerData {
    private final JavaPlugin plugin;
    private final File file;
    private final YamlConfiguration config;

    public PlayerData(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "players.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            config.set("default", false);
            save();
        }       
    }

    public synchronized void setPersonalKeepInv(Player player, boolean value) {
        config.set("players." + player.getUniqueId(), value);
        save();
    }

    public synchronized boolean getPersonalKeepInv(Player player) {
        return config.getBoolean("players." + player.getUniqueId(), getDefaultKeepInv());
    }

    public synchronized void resetPersonalKeepInv(Player player) {
        config.set("players." + player.getUniqueId(), null);
        save();
    }

    public synchronized boolean hasPersonalKeepInv(Player player) {
        return config.contains("players." + player.getUniqueId());
    }

    private synchronized void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save players.yml", e);
        }
    }

    public synchronized boolean getDefaultKeepInv() {
        return config.getBoolean("default", false);
    }

    public synchronized void setDefaultKeepInv(boolean value) {
        config.set("default", value);
        save();
    }
}
