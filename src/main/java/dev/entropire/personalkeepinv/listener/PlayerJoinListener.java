package dev.entropire.personalkeepinv.listener;

import java.net.http.WebSocket.Listener;

import org.bukkit.GameRules;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.entropire.personalkeepinv.PlayerData;

public class PlayerJoinListener implements Listener {

    private final PlayerData playerData;

    public PlayerJoinListener(PlayerData playerData){
        this.playerData = playerData;
    }

    @EventHandler 
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.hasPlayedBefore()){
            boolean defaultKeepInv = player.getWorld().getGameRuleValue(GameRules.KEEP_INVENTORY);
            playerData.setPersonalKeepInv(event.getPlayer(), defaultKeepInv);
        }
    }
}
