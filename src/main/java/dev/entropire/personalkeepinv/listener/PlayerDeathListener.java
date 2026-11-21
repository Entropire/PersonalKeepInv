package dev.entropire.personalkeepinv.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import dev.entropire.personalkeepinv.PlayerData;

public class PlayerDeathListener implements Listener{

    private final PlayerData playerData;
    private final GameRuleEnforcer gameRuleEnforcer;

    public PlayerDeathListener(PlayerData playerData, GameRuleEnforcer gameRuleEnforcer){
        this.playerData = playerData;
        this.gameRuleEnforcer = gameRuleEnforcer;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (playerData.getPersonalKeepInv(player)) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getDrops().clear();
            event.setDroppedExp(0);
        }else if (event.getKeepInventory()) {
            gameRuleEnforcer.enforce(player.getWorld());
        }
    }
}