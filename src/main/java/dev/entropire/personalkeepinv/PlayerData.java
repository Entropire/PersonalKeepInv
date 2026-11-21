package dev.entropire.personalkeepinv;

import org.bukkit.GameRules;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerData {
    private final NamespacedKey personalKeepInvKey;

    public PlayerData(JavaPlugin plugin){
        this.personalKeepInvKey = new NamespacedKey(plugin, "personal_keep_inv");
    }

    public void setPersonalKeepInv(Player player, boolean value){
        player.getPersistentDataContainer().set(
            personalKeepInvKey, 
            PersistentDataType.BOOLEAN, 
            value
        );
    }

    public boolean getPersonalKeepInv(Player player){
        boolean keepInv = player.getWorld().getGameRuleValue(GameRules.KEEP_INVENTORY);
        return player.getPersistentDataContainer().getOrDefault(
            personalKeepInvKey, 
            PersistentDataType.BOOLEAN, 
            keepInv
        );
    }
}
