package dev.entropire.personalkeepinv.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import dev.entropire.personalkeepinv.PlayerData;

public class PlayerDeathListener implements Listener{

    private final PlayerData playerData;

    public PlayerDeathListener(PlayerData playerData){
        this.playerData = playerData;
    }

    @EventHandler 
    public void onPlayerDeath(PlayerDeathEvent event){
        boolean playerKeepInv = playerData.getPersonalKeepInv(event.getPlayer());
        
        if(playerKeepInv){
            event.setKeepInventory(playerKeepInv);
            event.getDrops().clear();
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }
}