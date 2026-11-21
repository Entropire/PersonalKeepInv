package dev.entropire.personalkeepinv;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev.entropire.personalkeepinv.command.KeepInvCommand;
import dev.entropire.personalkeepinv.listener.PlayerDeathListener;

public final class PersonalKeepInv extends JavaPlugin implements Listener {

    private final PlayerData playerData;

    private PersonalKeepInv(){
       playerData = new PlayerData(this); 
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(playerData), this);

        getCommand("keepinv").setExecutor(new KeepInvCommand(playerData));
        getCommand("keepinv").setTabCompleter(new KeepInvCommand(playerData));
    }

    public PlayerData getPlayerData(){
        return this.playerData;
    }
}
