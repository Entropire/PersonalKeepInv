package dev.entropire.personalkeepinv.listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import dev.entropire.personalkeepinv.util.GameRuleHelper;

public class GameRuleEnforcer implements Listener {
    
    private final Logger logger;

    public GameRuleEnforcer(Logger logger) {
        this.logger = logger;
    }

    public void enforceAll() {
        for (World world : Bukkit.getWorlds()) {
            enforce(world);
        }
    }

    public void enforce(World world) {
        if (!GameRuleHelper.getKeepInventory(world)) {
            return;
        }
        GameRuleHelper.setKeepInventory(world, false);
        logger.warning("Set keepInventory to false in world '" + world.getName()
                + "'. PersonalKeepInv manages keepInventory now, use /keepinv default on/off instead.");
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        enforce(event.getWorld());
    }
}
